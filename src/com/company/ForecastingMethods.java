package com.company;

import java.util.Arrays;

public class ForecastingMethods {

    public static double printForecasts(LinkedList forecasts,double[] ForecastsTable,boolean forecast,boolean min,boolean max){
        if(forecast){
            System.out.println("""
                        
                        *******************************
                        EXPECTED SALES FOR NEXT 2 YEARS:
                        *******************************
                        """);
            forecasts.printList(forecasts,false);
        }if(min){
            return Arrays.stream(ForecastsTable).min().getAsDouble();
        }else if(max){
            return Arrays.stream(ForecastsTable).max().getAsDouble();
        }return 0;
    }
    public static double exponentialSmoothing(LinkedList dataset,boolean forecast,boolean min,boolean max){
        LinkedList.Node temp = dataset.head;
        double[] MSE = new double[dataset.size(dataset)];
        double lastDemand = Arrays.stream(temp.values).sum();
        double lastForecast = lastDemand;
        double[] ForecastsTable= new double[dataset.size(dataset)];
        LinkedList forecasts = new LinkedList(0);

        for(int i=0;i<dataset.size(dataset);i++){
            MSE[i] = (lastDemand-lastForecast)*(lastDemand-lastForecast);
            double Forecast = 0.2*lastDemand+0.8*lastForecast;
            ForecastsTable[i] = Forecast;
            lastForecast = Forecast;
            if(forecast){
                forecasts.insert(forecasts,new int[]{(int)Forecast},temp.year, temp.month);
            }
            temp=temp.next;
            if(temp!=null){
                lastDemand= Arrays.stream(temp.values).sum();
            }

        }if(forecast||max||min) return printForecasts(forecasts,ForecastsTable,forecast,min,max);
        return Arrays.stream(MSE).sum()/24;
    }
    public static double doubleExponential(LinkedList dataset,boolean forecast,boolean min,boolean max){
        LinkedList.Node temp = dataset.head;
        LinkedList forecasts = new LinkedList(0);
        double[] MSE = new double[dataset.size(dataset)];
        double lastDemand = Arrays.stream(temp.values).sum();
        double Forecast;
        double[] ForecastsTable= new double[dataset.size(dataset)];

        double ST_0 = 200;
        double GT_0 = 50;
        double lastST;
        double lastGT;
        double ST=ST_0;
        double GT= GT_0;

        for(int i=0;i<dataset.size(dataset);i++) {
            lastST = 0.2*lastDemand+0.8*(ST+GT);
            lastGT = 0.2*(lastST-ST)+0.8*GT;
            Forecast = ST+GT;
            ForecastsTable[i] = Forecast;
            if(forecast){
                forecasts.insert(forecasts,new int[]{(int)Forecast},temp.year, temp.month);
            }
            MSE[i] = (lastDemand-Forecast)*(lastDemand-Forecast);
            temp= temp.next;
            if(temp!=null){
                lastDemand= Arrays.stream(temp.values).sum();
            }ST= lastST;
            GT=lastGT;
        }if(forecast||max||min) return printForecasts(forecasts,ForecastsTable,forecast,min,max);
        return Arrays.stream(MSE).sum()/dataset.size(dataset);
    }
    public static double regressionAnalysis(LinkedList dataset,boolean forecast,boolean min,boolean max){
        double total = dataset.sumOfElements(dataset);
        LinkedList forecasts = new LinkedList(0);
        double[] MSE = new double[dataset.size(dataset)];
        double[] ForecastsTable= new double[dataset.size(dataset)];
        double y = total/dataset.size(dataset);

        double x = 12.5;

        double[] t_squares = new double[dataset.size(dataset)];

        double[] t_x_y = new double[dataset.size(dataset)];

        LinkedList.Node temp = dataset.head;

        for(int i=1;i<25;i++){
            t_squares[i-1] = i*i;
            t_x_y[i-1] = Arrays.stream(temp.values).sum()*i;
            temp= temp.next;
        }
        double b = (dataset.size(dataset)* Arrays.stream(t_x_y).sum()-(300*total))
                / (dataset.size(dataset)* Arrays.stream(t_squares).sum()-90000);

        double a = y-b*x;
        temp = dataset.head;
        for(int i=0;i<dataset.size(dataset);i++){
            ForecastsTable[i] = a+(i+1)*b;
            if(forecast){
                forecasts.insert(forecasts,new int[]{(int)(a+(i+1)*b)},temp.year, temp.month);
            }
            MSE[i] = ((Arrays.stream(temp.values).sum()-(a+(i+1)*b))
                        *(Arrays.stream(temp.values).sum()-(a+(i+1)*b)))/dataset.size(dataset);
            temp= temp.next;
         }
        if(forecast||max||min) return printForecasts(forecasts,ForecastsTable,forecast,min,max);

        return Arrays.stream(MSE).sum();
    }

    public static double deseasonalizedRegression(LinkedList dataset,boolean forecast,boolean min,boolean max){
        LinkedList.Node temp = dataset.head;
        LinkedList forecasts = new LinkedList(0);
        double[] MSE = new double[dataset.size(dataset)];

        double sum = dataset.sumOfElements(dataset);

        double x = 12.5;

        double y = sum/dataset.size(dataset);

        double[] t_squares = new double[dataset.size(dataset)];

        double[] t_x_y = new double[dataset.size(dataset)];

        double[] sumOfColumns = dataset.sumOfColumns(dataset);

        double[] period_avgs = new double[dataset.size(dataset)];

        double[] period_factors = new double[dataset.size(dataset)];

        double[] deason_demands = new double[dataset.size(dataset)];

        double[] seasonal_regressions = new double[dataset.size(dataset)];


        for(int i =0;i<dataset.size(dataset);i++){
            if(i<12){
                period_avgs[i] = (sumOfColumns[i]+sumOfColumns[i+12])/2;
            }else{
                period_avgs[i] = (sumOfColumns[i]+sumOfColumns[i-12])/2;
            }

            period_factors[i] = period_avgs[i]/(sum/dataset.size(dataset));

            deason_demands[i] = Arrays.stream(temp.values).sum()/period_factors[i];

            temp=temp.next;
        }

        temp=dataset.head;

        for(int i=1;i<dataset.size(dataset)+1;i++){
            t_squares[i-1] = i*i;
            t_x_y[i-1] = deason_demands[i-1]*i;
            temp= temp.next;
        }
        temp=dataset.head;

        double b = (dataset.size(dataset)* Arrays.stream(t_x_y).sum()-(300*sum))
                / (dataset.size(dataset)* Arrays.stream(t_squares).sum()-90000);

        double a = y-b*x;

        for(int i=0;i<dataset.size(dataset);i++){

            seasonal_regressions[i] = (a+(i+1)*b)
                    *period_factors[i];
            if(forecast){
                forecasts.insert(forecasts,new int[]{(int)seasonal_regressions[i]},temp.year, temp.month);
            }
            MSE[i] = (seasonal_regressions[i]- Arrays.stream(temp.values).sum())
                    *(seasonal_regressions[i]- Arrays.stream(temp.values).sum())
                    /dataset.size(dataset);
            temp=temp.next;
        }
        if(forecast||max||min) return printForecasts(forecasts,seasonal_regressions,forecast,min,max);
        return Arrays.stream(MSE).sum();
    }
}
