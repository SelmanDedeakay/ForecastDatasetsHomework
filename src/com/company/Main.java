package com.company;

import java.util.*;


public class Main {
    public static LinkedList selectDataset(Map<LinkedList,Integer> datasets,Scanner input){
        LinkedList selectedDataset= null;
        while(true){
            try{
                System.out.println("\nSelect the dataset.");
                for (LinkedList dataset : datasets.keySet()) {
                    System.out.println("-Dataset " + dataset.id);
                }System.out.print("\n--->");
                int selected = input.nextInt();
                while(!datasets.containsValue(selected)){
                    System.out.print("""
                            This dataset number is not available.
                            
                            Please enter a valid number.
                            
                            --->""");
                    selected =input.nextInt();
                }for(LinkedList linkedList : datasets.keySet()){
                    if(datasets.get(linkedList)==selected){
                        selectedDataset = linkedList;
                    }
                }return selectedDataset;

            }catch (Exception e){
                System.out.println("\nPlease enter an Integer.");
                input.nextLine();
            }
        }
    }
    public static void ImportPreDefinedDatasets(LinkedList dataset1,LinkedList dataset2){

        //Pre-Defined Datasets
        //YEAR-1
        dataset1.insert(dataset1,new int[]{300},1,1);
        dataset1.insert(dataset1,new int[]{350},1,2);
        dataset1.insert(dataset1,new int[]{330},1,3);
        dataset1.insert(dataset1,new int[]{340},1,4);
        dataset1.insert(dataset1,new int[]{390},1,5);
        dataset1.insert(dataset1,new int[]{430},1,6);
        dataset1.insert(dataset1,new int[]{480},1,7);
        dataset1.insert(dataset1,new int[]{460},1,8);
        dataset1.insert(dataset1,new int[]{490},1,9);
        dataset1.insert(dataset1,new int[]{510},1,10);
        dataset1.insert(dataset1,new int[]{550},1,11);
        dataset1.insert(dataset1,new int[]{560},1,12);


        dataset2.insert(dataset2,new int[]{200},1,1);
        dataset2.insert(dataset2,new int[]{300},1,2);
        dataset2.insert(dataset2,new int[]{250},1,3);
        dataset2.insert(dataset2,new int[]{600},1,4);
        dataset2.insert(dataset2,new int[]{650},1,5);
        dataset2.insert(dataset2,new int[]{670},1,6);
        dataset2.insert(dataset2,new int[]{400},1,7);
        dataset2.insert(dataset2,new int[]{440},1,8);
        dataset2.insert(dataset2,new int[]{430},1,9);
        dataset2.insert(dataset2,new int[]{900},1,10);
        dataset2.insert(dataset2,new int[]{980},1,11);
        dataset2.insert(dataset2,new int[]{990},1,12);

        //Year-2

        dataset1.insert(dataset1,new int[]{550},2,1);
        dataset1.insert(dataset1,new int[]{590},2,2);
        dataset1.insert(dataset1,new int[]{600},2,3);
        dataset1.insert(dataset1,new int[]{610},2,4);
        dataset1.insert(dataset1,new int[]{630},2,5);
        dataset1.insert(dataset1,new int[]{620},2,6);
        dataset1.insert(dataset1,new int[]{680},2,7);
        dataset1.insert(dataset1,new int[]{690},2,8);
        dataset1.insert(dataset1,new int[]{710},2,9);
        dataset1.insert(dataset1,new int[]{730},2,10);
        dataset1.insert(dataset1,new int[]{740},2,11);
        dataset1.insert(dataset1,new int[]{770},2,12);


        dataset2.insert(dataset2,new int[]{300},2,1);
        dataset2.insert(dataset2,new int[]{370},2,2);
        dataset2.insert(dataset2,new int[]{380},2,3);
        dataset2.insert(dataset2,new int[]{710},2,4);
        dataset2.insert(dataset2,new int[]{730},2,5);
        dataset2.insert(dataset2,new int[]{790},2,6);
        dataset2.insert(dataset2,new int[]{450},2,7);
        dataset2.insert(dataset2,new int[]{480},2,8);
        dataset2.insert(dataset2,new int[]{490},2,9);
        dataset2.insert(dataset2,new int[]{930},2,10);
        dataset2.insert(dataset2,new int[]{960},2,11);
        dataset2.insert(dataset2,new int[]{980},2,12);
    }
    public static void main(String[] args){
        int dataset_number = 3;
        Scanner input =new Scanner(System.in);
        DatasetHandler handler = new DatasetHandler();
        LinkedHashMap<LinkedList,Integer> datasets = new LinkedHashMap<>();
        LinkedList dataset1 = new LinkedList(1);
        LinkedList dataset2 = new LinkedList(2);
        datasets.put(dataset1,dataset1.id);
        datasets.put(dataset2,dataset2.id);
        ImportPreDefinedDatasets(dataset1,dataset2);
        while(true){
            try {
                System.out.print("""
                    \nPlease select an operation below.
                    1- Insert a New Dataset
                    2- Display Existing Datasets
                    3- Delete Dataset
                    4- Find Minimum of Dataset
                    5- Find Maximum of Dataset
                    6- Search for a Value on Dataset
                    7- Replace a Value on the Dataset
                    8- Print a Dataset in Reversed Order
                    9- Forecast Utilizing a Dataset
                    10- Find Minimum/Maximum Forecasted Sales Number
                    11- Exit
                    
                    --->""");
                int choice = input.nextInt();
                if(choice==1){
                    LinkedList dataset = handler.InsertNewDataset(dataset_number,input);
                    datasets.put(dataset,dataset.id);
                    dataset_number++;
                }else if(choice==2){
                    handler.listDatasets(datasets);
                }else if(choice==3) {
                    if (datasets.isEmpty()) {
                        System.out.println("\nNo datasets available.\n");
                    } else {
                        LinkedList deleted = selectDataset(datasets,input);
                        datasets.remove(deleted);
                            System.out.println("Dataset deleted.");
                        }
                }
                else if(choice==4){
                    LinkedList selected = selectDataset(datasets,input);
                    HashMap<Integer,String> min = handler.findMin(selected);
                    System.out.println("\nThe minimum value of Dataset "+ selected.id+"\nIn "+min.get(min.keySet().toArray()[0])+" : "+min.keySet().toArray()[0]+"\n");

                    }
                else if(choice==5){
                    LinkedList selected = selectDataset(datasets,input);
                    HashMap<Integer,String> max = handler.findMax(selected);
                    System.out.println("\nThe maximum value of Dataset "+ selected.id+"\nIn "+max.get(max.keySet().toArray()[0])+" : "+max.keySet().toArray()[0]+"\n");

                    }
                else if(choice==6) {
                    LinkedList selected = selectDataset(datasets,input);
                    System.out.print("\nEnter the value you searching for: \n\n--->");
                    int value = input.nextInt();
                    handler.searchValue(value, selected);
                    }
                else if(choice==7) {
                    LinkedList selected = selectDataset(datasets,input);
                    selected.printList(selected,false);
                    System.out.print("Enter the Year You Want to Replace Value From:\n-Year 1\n-Year 2\n--->");
                    int year= input.nextInt();
                    System.out.print("Enter the Month You Want to Replace Value From:\n");

                    for(int i = 1;i<handler.getMonths().size()+1;i++){
                        System.out.print(i+"-"+handler.getMonths().get(i)+"   ");
                        if(i%3==0){
                            System.out.println("\n");
                        }
                    }

                    int month= input.nextInt();
                    System.out.print("Enter the Value Do You Want to Replace:\n--->");
                    int value= input.nextInt();
                    System.out.print("Enter the Value You Want to Replace With "+value+":\n--->");
                    int new_value= input.nextInt();
                    handler.changeElement(selected,month,year,value,new_value);


                    }
                else if (choice==8){
                    LinkedList selected = selectDataset(datasets,input);
                    selected.printList(selected,true);
                }
                else if(choice==9){
                    LinkedList selected = selectDataset(datasets,input);
                    System.out.print("""
                                        Would you like to see forecasts in descending order?
                                        (1-YES,2-NO)
                                        
                                        --->""");
                    int descending = input.nextInt();
                    ForecastingMethods.chooseBestMethod(selected, descending == 1);

                }else if(choice==10){
                    LinkedList selected = selectDataset(datasets,input);

                    System.out.println("\nMinimum Forecasted Sales Number of Exponential Smoothing Method : "
                            + ForecastingMethods.exponentialSmoothing(selected,false,true,false,false));
                    System.out.println("\nMaximum Forecasted Sales Number of Exponential Smoothing Method : "
                            + ForecastingMethods.exponentialSmoothing(selected,false,false,true,false));

                    System.out.println("\nMinimum Forecasted Sales Number of Double-Exponential Smoothing Method : "
                            + ForecastingMethods.doubleExponential(selected,false,true,false,false));
                    System.out.println("\nMaximum Forecasted Sales Number of Double-Exponential Smoothing Method : "
                            + ForecastingMethods.doubleExponential(selected,false,false,true,false));

                    System.out.println("\nMinimum Forecasted Sales Number of Regression Analysis : "
                            + ForecastingMethods.regressionAnalysis(selected,false,true,false,false));
                    System.out.println("\nMaximum Forecasted Sales Number of Regression Analysis : "
                            + ForecastingMethods.regressionAnalysis(selected,false,false,true,false));

                    System.out.println("\nMinimum Forecasted Sales Number of Deseasonalized Regression Analysis : "
                            + ForecastingMethods.deseasonalizedRegression(selected,false,true,false,false));
                    System.out.println("\nMaximum Forecasted Sales Number of Deseasonalized Regression Analysis : "
                            + ForecastingMethods.deseasonalizedRegression(selected,false,false,true,false));

                        }
                else if(choice==11){
                    System.out.println("Exiting...");
                    break;
                }else{
                    System.out.println("You entered wrong operation.");
                }
            }catch (Exception e){
                System.out.println("Error occurred. Please try again.");
                input.nextLine();
            }
            }
    }
}

