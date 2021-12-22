package com.company;

import java.util.Arrays;

public class ForecastingMethods {
    public static double exponentialSmoothing(LinkedList dataset,boolean forecast,boolean min,boolean max){
        LinkedList.Node temp = dataset.head;
        double[] MSE = new double[24];
        double lastDemand = Arrays.stream(temp.values).sum();
        double lastForecast = lastDemand;
        double[] ForecastsTable= new double[24];
        LinkedList forecasts = new LinkedList(0);

        for(int i=0;i<24;i++){
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

        }if(forecast){
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
        }
        return Arrays.stream(MSE).sum()/24;
    }
    public static double doubleExponential(LinkedList dataset,boolean forecast,boolean min,boolean max){
        LinkedList.Node temp = dataset.head;
        LinkedList forecasts = new LinkedList(0);
        double[] MSE = new double[24];
        double lastDemand = Arrays.stream(temp.values).sum();
        double Forecast;
        double[] ForecastsTable= new double[24];

        double ST_0 = 200;
        double GT_0 = 50;
        double lastST;
        double lastGT;
        double ST=ST_0;
        double GT= GT_0;

        for(int i=0;i<24;i++) {
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
        }if(forecast){
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
        }
        return Arrays.stream(MSE).sum()/24;
    }
    public static double regressionAnalysis(LinkedList dataset,boolean forecast,boolean min,boolean max){
        double total = dataset.sumOfElements(dataset);
        LinkedList forecasts = new LinkedList(0);
        double[] MSE = new double[24];
        double[] ForecastsTable= new double[24];
        double y = total/24;

        double x = 12.5;

        double[] t_squares = new double[24];

        double[] t_x_y = new double[24];

        LinkedList.Node temp = dataset.head;

        for(int i=1;i<25;i++){
            t_squares[i-1] = i*i;
            t_x_y[i-1] = Arrays.stream(temp.values).sum()*i;
            temp= temp.next;
        }
        double b = (24* Arrays.stream(t_x_y).sum()-(300*total))
                / (24* Arrays.stream(t_squares).sum()-90000);

        double a = y-b*x;
        temp = dataset.head;
        for(int i=0;i<24;i++){
            ForecastsTable[i] = a+(i+1)*b;
            if(forecast){
                forecasts.insert(forecasts,new int[]{(int)(a+(i+1)*b)},temp.year, temp.month);
            }
            MSE[i] = ((Arrays.stream(temp.values).sum()-(a+(i+1)*b))
                        *(Arrays.stream(temp.values).sum()-(a+(i+1)*b)))/24;
            temp= temp.next;
         }
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
        }
        return Arrays.stream(MSE).sum();
    }

    public static double deseasonalizedRegression(LinkedList dataset,boolean forecast,boolean min,boolean max){
        LinkedList.Node temp = dataset.head;
        LinkedList forecasts = new LinkedList(0);
        double[] MSE = new double[24];

        double sum = dataset.sumOfElements(dataset);

        double x = 12.5;

        double y = sum/24;

        double[] t_squares = new double[24];

        double[] t_x_y = new double[24];

        double[] sumOfColumns = dataset.sumOfColumns(dataset);

        double[] period_avgs = new double[24];

        double[] period_factors = new double[24];

        double[] deason_demands = new double[24];

        double[] seasonal_regressions = new double[24];


        for(int i =0;i<24;i++){
            if(i<12){
                period_avgs[i] = (sumOfColumns[i]+sumOfColumns[i+12])/2;
            }else{
                period_avgs[i] = (sumOfColumns[i]+sumOfColumns[i-12])/2;
            }

            period_factors[i] = period_avgs[i]/(sum/24);

            deason_demands[i] = Arrays.stream(temp.values).sum()/period_factors[i];

            temp=temp.next;
        }


        temp=dataset.head;

        for(int i=1;i<25;i++){
            t_squares[i-1] = i*i;
            t_x_y[i-1] = deason_demands[i-1]*i;
            temp= temp.next;
        }
        temp=dataset.head;

        double b = (24* Arrays.stream(t_x_y).sum()-(300*sum))
                / (24* Arrays.stream(t_squares).sum()-90000);

        double a = y-b*x;



        for(int i=0;i<24;i++){

            seasonal_regressions[i] = (a+(i+1)*b)
                    *period_factors[i];
            if(forecast){
                forecasts.insert(forecasts,new int[]{(int)seasonal_regressions[i]},temp.year, temp.month);
            }
            MSE[i] = (seasonal_regressions[i]- Arrays.stream(temp.values).sum())
                    *(seasonal_regressions[i]- Arrays.stream(temp.values).sum())
                    /24;
            temp=temp.next;
        }

        if(forecast){
            System.out.println("""
                        
                        *******************************
                        EXPECTED SALES FOR NEXT 2 YEARS
                        *******************************
                        """);
            forecasts.printList(forecasts,false);
        }
        if(min){
            return Arrays.stream(seasonal_regressions).min().getAsDouble();
        }else if(max){
            return Arrays.stream(seasonal_regressions).max().getAsDouble();
        }

        return Arrays.stream(MSE).sum();
    }
}
