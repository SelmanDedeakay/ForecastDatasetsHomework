package com.company;

import java.util.Arrays;

public class LinkedList {

        Node head; // head of list
        int id;
        public LinkedList(int id){
            this.id=id;

        }
        // Linked list Node.
        // Node is a static nested class
        // so main() can access it
        static class Node {
            int[] values;
            int year;
            int month;
            Node next;

            // Constructor
            Node(int[] values,int year,int month)
            {
                this.values=values;
                this.month=month;
                this.year= year;
                this.next = null;
            }
        }

        // **************INSERTION**************

        // Method to insert a new node
        public void insert(LinkedList list,
                                        int[] values,int year,int month)
        {
            // Create a new node with given data
            Node new_node = new Node(values,year,month);
            new_node.next = null;

            // If the Linked List is empty,
            // then make the new node as head
            if (list.head == null) {
                list.head = new_node;
            }
            else {
                // Else traverse till the last node
                // and insert the new_node there
                Node last = list.head;
                while (last.next != null) {
                    last = last.next;
                }

                // Insert the new_node at last node
                last.next = new_node;
            }

        }public void push(LinkedList list,
                     int[] values,int year,int month){
            Node new_node = new Node(values,year,month);
            new_node.next = list.head;
            list.head = new_node;
        }

        // **************TRAVERSAL**************

        // Method to print the LinkedList.
        public void printList(LinkedList list,boolean reverse)
        {
            int lastYear=0;
            int temp = 0;
            Node currNode = list.head;
            if(reverse){
                LinkedList stack = new LinkedList(0);

                while(currNode!=null){
                    stack.push(stack,currNode.values,currNode.year,currNode.month);
                    currNode=currNode.next;
                }
                for(int i=1;i<25;i++){
                    if(i==1){
                        stack.deleteTop(stack,0,i);
                    }else if(i<=13){
                        stack.deleteTop(stack,2,i);
                    }else{
                        stack.deleteTop(stack,1,i);
                    }
                }
                return;
            }

            // Traverse through the LinkedList
            while (currNode != null) {
                // Print the data at current node

                if(currNode.year!= lastYear){
                    System.out.println("\nYEAR "+currNode.year+"\n");
                }System.out.print(new DatasetHandler().getMonths().get(currNode.month) + ": ");

                for(int element: currNode.values){
                    System.out.print(element+"  ");
                }
                if(++temp%3==0){
                    System.out.println("\n");
                }



                // Go to next node
                lastYear= currNode.year;
                currNode = currNode.next;

            }
        }
    public void deleteTop(LinkedList list,int lastYear,int counter)
    {
        Node currNode = list.head;

        list.head = currNode.next;

        if(currNode.year!= lastYear){
            System.out.println("\nYEAR "+currNode.year+"\n");
        }System.out.print(new DatasetHandler().getMonths().get(currNode.month) + ": ");

        for(int element: currNode.values){
            System.out.print(element+"  ");
        }
        if(counter%3==0){
            System.out.println("\n");
        }
    }

    public double sumOfElements(LinkedList dataset){
            Node temp = dataset.head;
            double total=0;
            while(temp!=null){
                total += Arrays.stream(temp.values).sum();
                temp= temp.next;
            }
            return total;
    }
    public double[] sumOfColumns(LinkedList dataset){
            double[] sums = new double[24];
            Node temp = dataset.head;
            for(int i=0;i<24;i++){
                sums[i]= Arrays.stream(temp.values).sum();
                temp=temp.next;
            }return sums;
    }
}

