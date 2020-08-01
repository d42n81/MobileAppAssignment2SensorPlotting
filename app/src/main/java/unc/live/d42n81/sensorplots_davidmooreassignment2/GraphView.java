package unc.live.d42n81.sensorplots_davidmooreassignment2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static android.graphics.Color.argb;

public class GraphView extends View {
    ArrayList<Float> dataList = new ArrayList<Float>(7);
    ArrayList<Float> averageList = new ArrayList<Float>(7);
    ArrayList<Float> varianceList = new ArrayList<Float>(7);
    float mean = 0;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // (0,0) is (100dp, 310dp)
        // (10,0) is (100 dp + 310dp, 310dp)

        // First, draw grid lines at specified place
        Paint whiteGridPaint = new Paint();
        whiteGridPaint.setColor(Color.WHITE);
        int gridLeft = 1;
        // Grid Left should change by some delta to get each gridline to fit.
        // It's like moving the margin over to fit more gird lines on the left.
        int gridTop = 930;
        int gridRight = gridLeft + 9;
        int gridBottom = 1;
        // Rect xStartGridLine = new Rect(gridLeft, gridTop, gridRight, gridBottom);
        // int endgridLeft = 920;
        // Rect xEndGridLine = new Rect(endgridLeft, gridTop, endgridLeft + 9, gridBottom);

        // Total width = 920px;
        // Total height = 930px;
        int deltaX = (920/6);
        // canvas.drawRect(xStartGridLine,whiteGridPaint);
        // canvas.drawRect(xEndGridLine, whiteGridPaint);

        // Draw x GridLines:
        for(int i = 0; i< 7; i++) {
            canvas.drawRect(new Rect(i*deltaX, gridTop, i*deltaX+9, gridBottom),
                    whiteGridPaint);
        }

        int deltaY = (921/6);

        // Draw Y GridLines:
        int gridYStartBottom = 921;
        int gridYEndBottom = 0;
        // Rect yStartGridLine = new Rect(0,gridYStartBottom + 9, 920, gridYStartBottom);
        // Rect yEndGridLine = new Rect(0, gridYEndBottom + 9, 920, gridYEndBottom);
        // canvas.drawRect(yStartGridLine, whiteGridPaint);
        // canvas.drawRect(yEndGridLine, whiteGridPaint);

        // Draw Y GridLines:
        for(int i = 0; i < 7; i++) {
            canvas.drawRect(new Rect(0, i*deltaY + 9, 920, i*deltaY),
                    whiteGridPaint);
        }

        // Now that GridLines have been drawn, draw points.
        // start by making the point colors:
        Paint dataPaint = new Paint();
        dataPaint.setColor(Color.argb(255, 102, 0, 102));
        Paint averagePaint = new Paint();
        averagePaint.setColor(Color.BLUE);
        Paint variancePaint = new Paint();
        variancePaint.setColor(Color.RED);

        for(int i = 0; i < dataList.size(); i++) {
            double x = deltaX * i;
            double oldX;
            double oldDataY = 0;
            double oldAverageY = 0;
            double oldVarianceY = 0;
            if (i != 0){
                oldX = deltaX *(i-1);
            } else {
                oldX = 0;

            }
            // The division by 10 will change to a different number if I change the scale of
            // the y-axis.
            double dataY = 921 - (deltaY * (dataList.get(i) / 10));
            if (i != 0) {
                oldDataY = 921 - (deltaY * (dataList.get(i-1) / 10));
                oldAverageY = 921 - (deltaY * (averageList.get(i-1) / 10));
                oldVarianceY = 921 - (deltaY * (varianceList.get(i-1) / 10));
            }

            double averageY = 921 - (deltaY * (averageList.get(i) / 10));
            double varianceY = 921 - (deltaY * (varianceList.get(i) / 10));
            int radius = 25;
            canvas.drawCircle((float) x, (float) dataY, radius, dataPaint);
            canvas.drawCircle((float) x, (float) averageY, radius, averagePaint);
            canvas.drawCircle((float) x, (float) varianceY, radius, variancePaint);
            if(i != 0) {
                canvas.drawLine((float) oldX, (float) oldDataY, (float) x, (float) dataY, dataPaint);
                canvas.drawLine((float) oldX, (float) oldAverageY, (float) x, (float) averageY,
                        averagePaint);
                canvas.drawLine((float) oldX, (float) oldVarianceY, (float) x, (float) varianceY,
                        variancePaint);
            }
            // Still need to draw lines between each point.
        }
        invalidate();

    }

    public void addPointToData(float num) {
        // int indexToInsert = findIndexToInsert();
        if(dataList.size() >= 7) {
            dataList.remove(0);
        }
        this.dataList.add(num);
        this.addPointToAverage(this.computeAverage());
        this.addPointToVariance(this.computeStandardDeviation());
    }

    public void addPointToAverage(float num) {
        if(averageList.size() >= 7) {
            averageList.remove(0);
        }
        this.averageList.add(num);
    }

    public float computeAverage(){
        float sum = 0;
        int totalNumbersUsed = 0;
        for(int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i) != null) {
                sum += dataList.get(i);
                totalNumbersUsed++;
            }
        }
        if(sum == 0 || totalNumbersUsed == 0) {
            return 0;
        }
        return sum/totalNumbersUsed;
    }

    public float computeStandardDeviation() {
        float meanAtPoint = 0;
        for(int i = 0; i <averageList.size(); i++) {
            if(averageList.get(i) != null) {
                meanAtPoint = averageList.get(i);
                this.mean = meanAtPoint;
            }
        }
        Log.v("MYTAG", "MeanAtPoint = " + meanAtPoint);
        // Now that we have the mean at Point, subtract the mean from each data
        // value, square the result, sum all results.
        float sumationOfResults = 0;
        int totalNumbersUsedForStandardDeviation = 0;
        float finalStandardDeviation = 0;
        for(int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i) != null) {
                float data = dataList.get(i);
                float subtractedData = data - meanAtPoint;
                float squaredData = subtractedData * subtractedData;
                totalNumbersUsedForStandardDeviation++;
                sumationOfResults += squaredData;

            }
        }
        if(totalNumbersUsedForStandardDeviation != 0) {
            float meanOfSquaredDifferences = sumationOfResults/totalNumbersUsedForStandardDeviation;
            finalStandardDeviation = (float) Math.sqrt((double)meanOfSquaredDifferences);
        }
        return finalStandardDeviation;
    }

    public void addPointToVariance(float num) {
        if(varianceList.size() >= 7) {
            varianceList.remove(0);
        }
        this.varianceList.add(num);
    }

    public float getMean() {
        return this.mean;
    }
}
