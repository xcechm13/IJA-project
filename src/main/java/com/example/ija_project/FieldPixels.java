package com.example.ija_project;

public class FieldPixels {
    public static boolean[][] CreateField (FieldNeighbour fieldNeighbour)
    {
        boolean field[][] = new boolean[Constants.FieldPixelsNum][Constants.FieldPixelsNum];
        switch (fieldNeighbour) {

            case NoNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case RightNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case LeftNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case LeftRightNeighbour -> {
                return new boolean[][]
                   {
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                   {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                   {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                   };
            }
            case DownNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                    };
            }
            case DownRightNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                    };
            }
            case DownLeftNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                    };
            }
            case DownLeftRightNeighbour -> {
                return new boolean[][]
                    {
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                    };
            }
            case UpNeighbour -> {
                return new boolean[][]
                    {
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case UpRightNeighbour -> {
                return new boolean[][]
                    {
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case UpLeftNeighbour -> {
                return new boolean[][]
                    {
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true,  false, false, false, false, false},
                    {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case UpLeftRightNeighbour -> {
                return new boolean[][]
                    {
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true },
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
            }
            case UpDownNeighbour -> {
                return new boolean[][]
                   {
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                   {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                   };
            }
            case UpDownRightNeighbour -> {
                return new boolean[][]
                    {
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                    };
            }
            case UpDownLeftNeighbour -> {
                return new boolean[][]
                    {
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                    {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false}
                    };
            }
            case UpDownLeftRightNeighbour -> {
                return new boolean[][]
                {
                {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {true,  true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  true },
                {false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,  false},
                };
            }
        }
        return new boolean[][]
               {
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
               };
    }
}
