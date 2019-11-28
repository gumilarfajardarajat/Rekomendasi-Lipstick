package com.google.firebase.samples.apps.mlkit.java.facedetection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;
import com.google.firebase.samples.apps.mlkit.common.GraphicOverlay;
import com.google.firebase.samples.apps.mlkit.common.GraphicOverlay.Graphic;

/** Graphic instance for rendering face contours graphic overlay view. */
public class FaceContourGraphic extends Graphic {

  private static final float FACE_POSITION_RADIUS = 4.0f;
  private static final float ID_TEXT_SIZE = 30.0f;
  private static final float ID_Y_OFFSET = 80.0f;
  private static final float ID_X_OFFSET = -70.0f;
  private static final float BOX_STROKE_WIDTH = 5.0f;

  private final Paint facePositionPaint;
  private final Paint idPaint;
  private final Paint boxPaint;

  private volatile FirebaseVisionFace firebaseVisionFace;

  public FaceContourGraphic(GraphicOverlay overlay, FirebaseVisionFace face) {
    super(overlay);

    this.firebaseVisionFace = face;
    final int selectedColor = Color.BLUE;

    facePositionPaint = new Paint();
    facePositionPaint.setColor(selectedColor);

    idPaint = new Paint();
    idPaint.setColor(selectedColor);
    idPaint.setTextSize(ID_TEXT_SIZE);

    boxPaint = new Paint();
    boxPaint.setColor(selectedColor);
    boxPaint.setStyle(Paint.Style.STROKE);
    boxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
  }

  /** Draws the face annotations for position on the supplied canvas. */
  @Override
  public void draw(Canvas canvas) {
    FirebaseVisionFace face = firebaseVisionFace;
    if (face == null) {
      return;
    }

    final Paint paint = new Paint();

    paint.setColor(Color.parseColor("#F7C5B4"));
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setAlpha(128);



//    FirebaseVisionFaceContour contour = face.getContour(FirebaseVisionFaceContour.ALL_POINTS);
//    for (com.google.firebase.ml.vision.common.FirebaseVisionPoint point : contour.getPoints()) {
//      float px = translateX(point.getX());
//      float py = translateY(point.getY());
//      canvas.drawCircle(px, py, FACE_POSITION_RADIUS, facePositionPaint);
//    }

    //Pola Pertama

    final Path path = new Path();

    FirebaseVisionFaceContour upperLipTop = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_TOP);
    int index = 0;
    for (com.google.firebase.ml.vision.common.FirebaseVisionPoint point : upperLipTop.getPoints()) {
      float px = translateX(point.getX());
      float py = translateY(point.getY());

      if(index == 0){
        path.moveTo(px, py);
      }else{
        path.lineTo(px, py);
      }

      index++;
    }

    FirebaseVisionFaceContour lowerLipBottom = face.getContour(FirebaseVisionFaceContour.LOWER_LIP_BOTTOM);

    for (com.google.firebase.ml.vision.common.FirebaseVisionPoint point : lowerLipBottom.getPoints()) {
      float px = translateX(point.getX());
      float py = translateY(point.getY());

      path.lineTo(px, py);

    }

    path.close();


    FirebaseVisionFaceContour upperLipBottom = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_BOTTOM);
    index = 0;
    for (com.google.firebase.ml.vision.common.FirebaseVisionPoint point : upperLipBottom.getPoints()) {
      float px = translateX(point.getX());
      float py = translateY(point.getY());

      if(index == 0){
        path.moveTo(px, py);
      }else{
        path.lineTo(px, py);
      }

      index++;
    }

    FirebaseVisionFaceContour lowerLipTop= face.getContour(FirebaseVisionFaceContour.LOWER_LIP_TOP);

    for (com.google.firebase.ml.vision.common.FirebaseVisionPoint point : lowerLipTop.getPoints()) {
      float px = translateX(point.getX());
      float py = translateY(point.getY());

      path.lineTo(px, py);

    }

    path.close();

    path.setFillType(Path.FillType.EVEN_ODD);
    canvas.drawPath(path, paint);

  }
}
