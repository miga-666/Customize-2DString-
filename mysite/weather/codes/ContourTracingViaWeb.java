import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
public class ContourTracingViaWeb {
    public static String[]StringForResultX = new String[10];
    public static String[]StringForResultMaxX = new String[10];
    public static String[]StringForResultY = new String[10];
    public static String[]StringForResultMaxY = new String[10];
    public static void printAfterContour(int[][] data){
        for(int i = 0 ; i < data.length ; i ++){
            for(int j = 0 ; j < data.length ; j ++){
                if (data[i][j] < 0){
                    System.out.print("-"+" ");
                }else{
                    System.out.print(data[i][j]+" ");
                }
            } 
            System.out.println();
        }
    }
    public static void print(int[][] data){
        for(int i = 0 ; i < data.length ; i ++){
            for(int j = 0 ; j < data.length ; j ++){
                System.out.print(data[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static int[][] defineValue(int[][] data, String level){
        for(int i = 0 ; i < data.length ; i ++){
            for(int j = 0 ; j < data.length ; j ++){
                if(segment(data[i][j]).compareTo(level)== 0){
                    data[i][j] = 1;
                }else{
                    data[i][j] = 0;
                }
            }
        }
        return data;
    }
    
    public static int[][] checkInside(int[][] data, int y, int x){
        if (y-1 > 1 && data[y-1][x] == 1){
            data[y-1][x] = -1;
            checkInside(data, y-1, x);
        }else if (y+1 < data.length-1 && data[y+1][x] == 1){
            data[y+1][x] = -1;
            checkInside(data, y+1, x);
        }else if (x+1 < data.length-1 && data[y][x+1] == 1){
            data[y][x+1] = -1;
            checkInside(data, y, x+1);
        }else if (x-1 > 1 && data[y][x-1] == 1){
            data[y][x-1] = -1;
            checkInside(data, y, x-1);
        }else{
            return data;
        }
        return data;
    }
    public static int[][] check(int[][] data, String grade){
        SquareTracingService s = new SquareTracingService();
        List points = s.getContourPoints(data);
        Iterator iterator = points.iterator();
        
        PointInfo[] Allpoint = new PointInfo[points.size()/2];
        int i = 0;
		while (iterator.hasNext()){
            int y = Integer.parseInt(iterator.next().toString());
            int x = Integer.parseInt(iterator.next().toString());
            if (data[y][x] != -1){
                Allpoint[i] = new PointInfo(y, x);
            }
            data[y][x] = -1;
            i = i + 1;
        }
        
        int maxIndex = Allpoint[0].x;
        int minIndex = Allpoint[0].x;
        int maxIndexY = Allpoint[0].y;
        int minIndexY = Allpoint[0].y;
        for (int j = 0 ; j < Allpoint.length; j ++){
            if (Allpoint[j] == null){
                continue;
            }
            if(maxIndex < Allpoint[j].x){
                maxIndex = Allpoint[j].x;
            }else if(minIndex > Allpoint[j].x){
                minIndex = Allpoint[j].x;
            }
            if(maxIndexY < Allpoint[j].y){
                maxIndexY = Allpoint[j].y;
            }else if(minIndexY > Allpoint[j].y){
                minIndexY = Allpoint[j].y;
            }
            checkInside(data, Allpoint[j].x, Allpoint[j].y);
        }
        /*Min X*/ 
        
        if (StringForResultX[minIndex-1] != ""){
            StringForResultX[minIndex-1] = StringForResultX[minIndex-1] +grade + "b";
        }else{
            StringForResultX[minIndex-1] = grade + "b";
        }

        /** Max X*/ 
        
        if (StringForResultMaxX[maxIndex-1] != ""){
            StringForResultMaxX[maxIndex-1] = StringForResultMaxX[maxIndex-1] +grade + "e";
        }else{
            StringForResultMaxX[maxIndex-1] = grade + "e";
        }

        /** Min Y*/
        
        
        if (StringForResultY[minIndexY-1] != ""){
            StringForResultY[minIndexY-1] = StringForResultY[minIndexY-1] +grade + "b";
        }else{
            StringForResultY[minIndexY-1] = grade + "b";
        }

        /* Max Y*/
        
        if (StringForResultMaxY[maxIndexY-1] != ""){
            StringForResultMaxY[maxIndexY-1] = StringForResultMaxY[maxIndexY-1] +grade + "e";
        }else{
            StringForResultMaxY[maxIndexY-1] = grade + "e";
        }
        
        return data;
    }

    public static boolean test(int[][] data){
        boolean oneOrNot = false;
        for(int i = 0 ; i < data.length ; i ++){
            for(int j = 0 ; j < data.length ; j ++){
                if (data[i][j] == 1){
                    oneOrNot = true;
                    return oneOrNot;
                }
            }
        }
        return oneOrNot;
    }

    public static String segment(int data){
        /*
          1	     2	    3	??? 11
          ?C	 ?C	    ?C	
         0-11  12-23  24-35	
          4	     5	    6	??? 5
          ??	 ??	    ??	
        36-41  42-47  48-53	
          7	     8	    9	    10  
          ??	 ??	    ??	    ????
        54-58  59-64  65-70	    >71
        */
        // 10 level 
        // less than 150 
        // 1 - 7.xxx
        if (data == -1){
            return "None";
        }else if (data != -1 && data < 7.5){
            return "Good1";
        }else if (data > 7.4 && data < 15.5){
            return "Good2";
        }else if (data > 15.4 && data < 25.5){
            return "Moderate1";
        }else if (data > 25.4 && data < 35.5){
            return "Moderate2";
        }else if (data > 35.4 && data < 45.5){
            return "Unhealthy for Sensitive Groups1";
        }else if (data > 45.4 && data < 54.5){
            return "Unhealthy for Sensitive Groups2";
        }else if (data > 54.4 && data < 102.5){
            return "Unhealthy1";
        }else if (data > 102.4 && data < 150.5){
            return "Unhealthy2";
        }else if (data > 150.4 && data < 250.5){
            return "Very Unhealthy";
        }else{
            return "Hazardous";
        }
    }
    
    
    public static int[][] dataWithBorder(int[][] input){
        int[][] data = new int[12][12];
        for (int i = 0 ;i < 12; i++){
            data[0][i] = 0;
            data[11][i] = 0;
            data[i][0] = 0;
            data[i][11] = 0;
        }
        for (int i = 0 ; i < 10 ; i ++){
            for (int j = 0 ; j < 10; j ++){
                data[i+1][j+1] = input[i][j];
            }
        }
        return data;
    }
    public static void main(String[] argv){
        // read data
        int number = 0;
        
        
        int[][] inputData = new int[10][10];
        for(int i = 0 ; i < argv.length; i ++){
            inputData[i/10][i%10] = Integer.parseInt(argv[i]);
        }

        for (int i = 0 ; i < StringForResultX.length; i ++){
            StringForResultX[i] = "";
            StringForResultY[i] = "";
            StringForResultMaxX[i] = "";
            StringForResultMaxY[i] = "";
        }
        
        // input an array of data
        String grading[] = {"Good1", "Good2", "Moderate1", "Moderate2", "Unhealthy for Sensitive Groups1","Unhealthy for Sensitive Groups2", "Unhealthy1","Unhealthy2","Very Unhealthy","Hazardous"};

        System.out.println("Start");
        // int[][] dataPM25 = files[number].returnData();
        int[][] dataPM25 = dataWithBorder(inputData);
        int[][] eachGrade = new int[dataPM25.length][dataPM25.length];
        int[][] dataPM25_clone = new int[dataPM25.length][dataPM25.length];
        for(int i = 0 ; i < dataPM25.length; i++){
            for(int j = 0 ; j < dataPM25.length; j++){
                dataPM25_clone[i][j] = dataPM25[i][j];
            }
        }
        String[] stringForGrade = {"A","B","C","D","E","F","G","H","I","J"};
        for (int i = 1 ; i <= grading.length; i++){
            defineValue(dataPM25,grading[i-1]);
            try{
                int count = 1 ;
                while (test(dataPM25)){
                    check(dataPM25,stringForGrade[i-1]);
                    count = count +1;
                }
                for(int j = 0 ; j < dataPM25.length ; j ++){
                    for(int k = 0 ; k < dataPM25.length ; k ++){
                        if (dataPM25[j][k] == -1){
                            eachGrade[j][k] = i;
                        }
                    }
                }
            }catch(NullPointerException e){
                ;
            }
            for(int a = 0 ; a < dataPM25.length; a++){
                for(int b = 0 ; b < dataPM25.length; b++){
                    dataPM25[a][b] = dataPM25_clone[a][b];
                }
            }
        }
        /** add mountain for x in math */
        int[][] mountainX = {{0,0},{0,3},{9,9},{8,9}};
        int[][] mountainY = {{0,0},{5,9},{0,1},{3,8}};
        for(int i = 0 ; i < mountainX.length; i ++){
            
            if(StringForResultY[mountainX[i][0]] != ""){
                StringForResultY[mountainX[i][0]] = StringForResultY[mountainX[i][0]] + "Mb";
            }else{
                StringForResultY[mountainX[i][0]] = "Mb";
            }
            if(StringForResultMaxY[mountainX[i][1]] != ""){
                StringForResultMaxY[mountainX[i][1]] = StringForResultMaxY[mountainX[i][1]] + "Me";
            }else{
                StringForResultMaxY[mountainX[i][1]] = "Me";
            }
            
            /** add mountain for y in math */
            if(StringForResultX[mountainY[i][0]] != ""){
                StringForResultX[mountainY[i][0]] = StringForResultX[mountainY[i][0]] + "Mb";
            }else{
                StringForResultX[mountainY[i][0]] = "Mb";
            }
            if(StringForResultMaxX[mountainY[i][1]] != ""){
                StringForResultMaxX[mountainY[i][1]] = StringForResultMaxX[mountainY[i][1]] + "Me";
            }else{
                StringForResultMaxX[mountainY[i][1]] = "Me";
            }
            
        }
        
        
        // River
        /** add river for x in math */
        int[][] riverX = {{0,2},{3,5},{6,9}};
        int[][] riverY = {{1,2},{0,0},{1,2}};
        for(int i = 0 ; i < riverX.length; i ++){
            
            if(StringForResultY[riverX[i][0]] != ""){
                StringForResultY[riverX[i][0]] = StringForResultY[riverX[i][0]] + "Rb";
            }else{
                StringForResultY[riverX[i][0]] = "Rb";
            }
            if(StringForResultMaxY[riverX[i][1]] != ""){
                StringForResultMaxY[riverX[i][1]] = StringForResultMaxY[riverX[i][1]] + "Re";
            }else{
                StringForResultMaxY[riverX[i][1]] = "Re";
            }
            
            /** add mountain for y in math */
            if(StringForResultX[riverY[i][0]] != ""){
                StringForResultX[riverY[i][0]] = StringForResultX[riverY[i][0]] + "Rb";
            }else{
                StringForResultX[riverY[i][0]] = "Rb";
            }
            if(StringForResultMaxX[riverY[i][1]] != ""){
                StringForResultMaxX[riverY[i][1]] = StringForResultMaxX[riverY[i][1]] + "Re";
            }else{
                StringForResultMaxX[riverY[i][1]] = "Re";
            }
            
        }
        String x = "";
        String y = "";
        int count = 0;
        //int CountY = 0;
        while(count < StringForResultY.length){
            if (StringForResultY[count] != ""){
                StringForResultY[count] = "#" + StringForResultY[count];
                x += StringForResultY[count];
            }
            
            if (StringForResultMaxY[count] != "" ){
                StringForResultMaxY[count] = "#" + StringForResultMaxY[count];
                x += StringForResultMaxY[count];
            }
            
                
            if (StringForResultX[count] != "" ){
                StringForResultX[count] = "#" + StringForResultX[count];
                y += StringForResultX[count];
            }
            
            
            if (StringForResultMaxX[count] != ""){
                StringForResultMaxX[count] = "#" + StringForResultMaxX[count];
                y += StringForResultMaxX[count];
            }
            count = count + 1;
        }
        /**String for x in "math" */
        System.out.println(x + " " + y );
        System.out.println("Finish");
        for (int j = 0 ; j < StringForResultY.length; j ++){
            StringForResultY[j] = "";
            StringForResultX[j] = "";
            StringForResultMaxY[j] = "";
            StringForResultMaxX[j] = "";
        }
        
    }
}
class PointInfo{
    int x = 0;
    int y = 0;
    
    public PointInfo(int x, int y){
        this.x = x;
        this.y = y;
    }
}

