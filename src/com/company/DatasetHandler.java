package com.company;

import java.util.*;

public class DatasetHandler {
    /*public void sortList(LinkedList dataset)
    {

        // Node current will point to head
        LinkedList.Node current = dataset.head, index;

        int[] temp;
        int lastYear=current.year;
        if (dataset.head == null) {
            return;
        }
        else {
            while (current != null) {
                // Node index will point to node next to
                // current
                index = current.next;

                while (index != null) {
                    // If current node's data is greater
                    // than index's node data, swap the data
                    // between them
                    if (lastYear== index.year){
                        if (Arrays.stream(current.values).sorted().sum() > Arrays.stream(index.values).sum()) {
                            temp = current.values;
                            current.values = index.values;
                            current.month= index.month;
                            current.year = index.year;
                            index.values = temp;
                        }
                    }


                    index = index.next;
                }
                current = current.next;
            }
        }
    }*/

    public void changeElement(LinkedList dataset,int month,int year,int value,int new_value){
        LinkedList.Node temp =dataset.head;
        while(temp!=null){
            if(year== temp.year && month== temp.month){
                for(int i=0;i<temp.values.length;i++){
                    if(temp.values[i]==value) {
                        temp.values[i] = new_value;
                        return;
                    }

                }
            }
            temp=temp.next;
        }
    }
    public void searchValue(int value,LinkedList dataset){
        LinkedList.Node temp = dataset.head;
        while(temp!=null){
            for(int element : temp.values) {
                if(element==value){
                    System.out.println("Dataset " + dataset.id + " contains the value " + value
                            + " in " + getMonths().get(temp.month) + " "+ temp.year+". Year ");
                    return;
                }
            }temp = temp.next;

        }
        System.out.println("Dataset "+dataset.id+" doesn't contain the value "+ value);
    }
    public HashMap<Integer,String> getMonths(){
        HashMap<Integer,String> months = new HashMap<Integer,String>();
        months.put(1,"January");
        months.put(2,"February");
        months.put(3,"March");
        months.put(4,"April");
        months.put(5,"May");
        months.put(6,"June");
        months.put(7,"July");
        months.put(8,"August");
        months.put(9,"September");
        months.put(10,"October");
        months.put(11,"November");
        months.put(12,"December");
        return months;
    }
    public LinkedList InsertNewDataset(int datasetNumber,Scanner scanner){

        System.out.print("How many values will be in a month? :");
        int frequency = scanner.nextInt();
        while(30%frequency!=0){
            System.out.println("Number of values must be a divisor of 30.");
            System.out.print("How many values will be in a month? :");
            frequency = scanner.nextInt();
        }

        LinkedList new_dataset = new LinkedList(datasetNumber);
        int value = 0;

        for(int y=1;y<=2;y++){
            System.out.println("YEAR "+ y);
            for(int i = 1;i<13;i++){
                int[] values = new int[frequency];
                for(int j = 1;j<=frequency;j++){
                    System.out.print(j+"."+ " value of "+getMonths().get(i)+" :");
                    value = scanner.nextInt();
                    values[j-1] = value;
                }new_dataset.insert(new_dataset,values,y,i);
            }
        }

        return new_dataset;
    }

    public HashMap<Integer, String> findMin(LinkedList dataset){
        LinkedList.Node temp = dataset.head;
        int min = temp.values[0];
        String min_month = getMonths().get(temp.month);
        while(temp!=null){
            for(int i : temp.values){
                if (min>i){
                    min=i;
                    min_month = getMonths().get(temp.month);
                }
            }temp=temp.next;
        }
        HashMap<Integer,String> map = new HashMap<>();
        map.put(min,min_month);
        return map;
    }
    public HashMap<Integer,String> findMax(LinkedList dataset){
        LinkedList.Node temp = dataset.head;
        int max = temp.values[0];
        String max_month =getMonths().get(temp.month);
        while(temp!=null){
            for(int i : temp.values){
                if (max<i){
                    max=i;
                    max_month = getMonths().get(temp.month);
                }
            }temp=temp.next;
        }

        HashMap<Integer,String> map = new HashMap<>();
        map.put(max,max_month);
        return map;
    }
    public void listDatasets(ArrayList<LinkedList> datasets){
        if(datasets.isEmpty()){
            System.out.println("\nNo datasets available.\n");
            return;
        }
        for(LinkedList lst: datasets){
            System.out.println("\nDataset "+ lst.id);
            lst.printList(lst,false);

        }
    }
}
