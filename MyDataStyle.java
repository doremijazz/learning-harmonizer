package com.example.learningharmonizer;

import java.util.ArrayList;

final class MyDataStyle {
        private final ArrayList<String> accords_du_style;
        private final String[][][] algo_du_style;
        private final String [] degres_du_style;

        public MyDataStyle(ArrayList<String> first, String[][][] second, String[] third) {
            this.accords_du_style = first;
            this.algo_du_style = second;
            this.degres_du_style = third;
        }

        public ArrayList<String> getFirst() {
            return accords_du_style;
        }

        public String[][][] getSecond() {
            return algo_du_style;
        }

        public String[] getThird (){return degres_du_style;}
    }

