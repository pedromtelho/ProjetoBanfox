package br.edu.insper.al.vitorge.banfox;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CamOne extends AppCompatActivity {
    private Button btnCapture;
    private TextureView textureView;
    private Button buttonSwitch;
    private static final String CAMERA_FRONT = "1";
    private static final String CAMERA_BACK = "0";
    private int pictureNumber;
    private Class nextClass;

    private Button buttonSend;
    private Button buttonDel;


    //Check do estado de orientação dos outputs das imagens
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private String cameraId = CAMERA_FRONT;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    //Salvar na pasta
    private File file;
    private static final int REQUEST_CAMERA_PREMISSION = 200;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private byte[] bytes;
    private ByteBuffer buffer;



    CameraDevice.StateCallback stateCallBack = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            textureView.setVisibility(View.VISIBLE);
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int i) {
            cameraDevice.close();
            cameraDevice=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_one);

        pictureNumber = ((Global) this.getApplication()).getPictureNumber();

        switch (pictureNumber) {
            case 0:
                nextClass = FunctionalityInformation.class;
                break;
            case 1:
                nextClass = FunctionalityInformation.class;
                break;
            case 2:
                nextClass = LoadingInformations.class;
                break;
            default:
                break;
        }

        textureView = findViewById(R.id.textureView);
        assert textureView != null;

        buttonSwitch = findViewById(R.id.button_switch_camera);
        buttonSend = findViewById(R.id.buttonSendPhoto);
        buttonDel = findViewById(R.id.buttonDelPhoto);

        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        textureView.setSurfaceTextureListener(textureListener);
        btnCapture = findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
                btnCapture.setVisibility(View.INVISIBLE);
                buttonSwitch.setVisibility(View.INVISIBLE);
                buttonSend.setVisibility(View.VISIBLE);
                buttonDel.setVisibility(View.VISIBLE);

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto(bytes);
                switch (pictureNumber) {
                    case 0:
                        ((Global) CamOne.this.getApplication()).setFacePicture(buffer);
                        break;
                    case 1:
                        ((Global) CamOne.this.getApplication()).setIdPicture(buffer);
                        break;
                    case 2:
                        ((Global) CamOne.this.getApplication()).setGroupPicture(buffer);
                        break;
                    default:
                        break;
                }
                ((Global) CamOne.this.getApplication()).setPictureNumber(pictureNumber + 1);
                Intent intent = new Intent(CamOne.this, nextClass);
                startActivity(intent);
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCameraPreview();
                btnCapture.setVisibility(View.VISIBLE);
                buttonSwitch.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.INVISIBLE);
                buttonDel.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void takePicture() {
        if(cameraDevice==null)
            return;
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if(characteristics!=null)
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);

                //Capturar imagem com tamanho customizado
                int width = 480;
                int height = 640;
                if(jpegSizes!=null && jpegSizes.length > 0){
                    width = jpegSizes[0].getWidth();
                    height = jpegSizes[0].getHeight();
                }

                //Como as imagens estão sendo interpretadas
                ImageReader reader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
                List<Surface> outputSurface = new ArrayList<>(2);
                outputSurface.add(reader.getSurface());
                outputSurface.add(new Surface(textureView.getSurfaceTexture()));
                final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(reader.getSurface());
                captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);


                //Check da orientação baseada no aparelho
                int rotation = getWindowManager().getDefaultDisplay().getRotation();

                if (cameraDevice.getId() == "1"){
                    captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, 270);
                }
                else{
                    captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
                }

                file = new File(Environment.getExternalStorageDirectory()+"/"+UUID.randomUUID().toString()+".jpg");
                ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener(){
                    @Override
                    public void onImageAvailable(ImageReader reader) {
                        Image image;
                        image = reader.acquireLatestImage();
                        buffer = image.getPlanes()[0].getBuffer();
                        bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                    }
                };
                reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);

                final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {

                    }
                };

                cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        try{
                            cameraCaptureSession.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                        }
                        catch (CameraAccessException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                    }
                }, mBackgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void savePhoto(byte[] bytes){
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (outputStream!=null)
                try{
                    outputStream.close();
                }
            catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void createCameraPreview() {
        try{
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (cameraDevice == null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(CamOne.this, "Changed", Toast.LENGTH_SHORT).show();
                }
            }, null);
        }
        catch(CameraAccessException e){
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (cameraDevice == null)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        try{
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        }
        catch (CameraAccessException e){
            e.printStackTrace();
        }
    }


    private void openCamera(){
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
//            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];

            //Check do realtime permission se está rodando na API mais atual que a 23

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CAMERA_PREMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallBack, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PREMISSION)
        {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        startBackgroundThread();
        if (textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(textureListener);
    }

    @Override
    protected void onPause(){
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    public void switchCamera() {
        System.out.println(cameraId);
        if (cameraId.equals(CAMERA_FRONT)) {
            cameraId = CAMERA_BACK;
            closeCamera();
            reopenCamera();
        }
        else if (cameraId.equals(CAMERA_BACK)) {
            cameraId = CAMERA_FRONT;
            closeCamera();
            reopenCamera();
        }
    }

    public void reopenCamera() {
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

}
