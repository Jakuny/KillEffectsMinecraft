package com.Jakuny.killeffects.utils;

public class ParticleFont {

    private static boolean[][] build(String... rows) {
        boolean[][] matrix = new boolean[5][5];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                matrix[r][c] = (rows[r].charAt(c) == '#');
            }
        }
        return matrix;
    }


    public static final boolean[][] A = build(
            ".###.",
            "#...#",
            "#####",
            "#...#",
            "#...#"
    );

    public static final boolean[][] B = build(
            "####.",
            "#...#",
            "####.",
            "#...#",
            "####."
    );

    public static final boolean[][] C = build(
            ".###.",
            "#...#",
            "#....",
            "#...#",
            ".###."
    );

    public static final boolean[][] D = build(
            "####.",
            "#...#",
            "#...#",
            "#...#",
            "####."
    );

    public static final boolean[][] E = build(
            "#####",
            "#....",
            "####.",
            "#....",
            "#####"
    );

    public static final boolean[][] F = build(
            "#####",
            "#....",
            "####.",
            "#....",
            "#...."
    );

    public static final boolean[][] G = build(
            ".###.",
            "#....",
            "#.###",
            "#...#",
            ".###."
    );

    public static final boolean[][] H = build(
            "#...#",
            "#...#",
            "#####",
            "#...#",
            "#...#"
    );

    public static final boolean[][] I = build(
            "#####",
            "..#..",
            "..#..",
            "..#..",
            "#####"
    );

    public static final boolean[][] J = build(
            "..###",
            "....#",
            "....#",
            "#...#",
            ".###."
    );

    public static final boolean[][] K = build(
            "#...#",
            "#..#.",
            "###..",
            "#..#.",
            "#...#"
    );

    public static final boolean[][] L = build(
            "#....",
            "#....",
            "#....",
            "#....",
            "#####"
    );

    public static final boolean[][] M = build(
            "#...#",
            "##.##",
            "#.#.#",
            "#...#",
            "#...#"
    );

    public static final boolean[][] N = build(
            "#...#",
            "##..#",
            "#.#.#",
            "#..##",
            "#...#"
    );

    public static final boolean[][] O = build(
            ".###.",
            "#...#",
            "#...#",
            "#...#",
            ".###."
    );

    public static final boolean[][] P = build(
            "####.",
            "#...#",
            "####.",
            "#....",
            "#...."
    );

    public static final boolean[][] Q = build(
            ".###.",
            "#...#",
            "#...#",
            ".###.",
            "...##"
    );

    public static final boolean[][] R = build(
            "####.",
            "#...#",
            "####.",
            "#..#.",
            "#...#"
    );

    public static final boolean[][] S = build(
            ".####",
            "#....",
            ".###.",
            "....#",
            "####."
    );

    public static final boolean[][] T = build(
            "#####",
            "..#..",
            "..#..",
            "..#..",
            "..#.."
    );

    public static final boolean[][] U = build(
            "#...#",
            "#...#",
            "#...#",
            "#...#",
            ".###."
    );

    public static final boolean[][] V = build(
            "#...#",
            "#...#",
            "#...#",
            ".#.#.",
            "..#.."
    );

    public static final boolean[][] W = build(
            "#...#",
            "#...#",
            "#.#.#",
            "##.##",
            "#...#"
    );

    public static final boolean[][] X = build(
            "#...#",
            ".#.#.",
            "..#..",
            ".#.#.",
            "#...#"
    );

    public static final boolean[][] Y = build(
            "#...#",
            ".#.#.",
            "..#..",
            "..#..",
            "..#.."
    );

    public static final boolean[][] Z = build(
            "#####",
            "...#.",
            "..#..",
            ".#...",
            "#####"
    );

    public static final boolean[][] SPACE = build(
            ".....",
            ".....",
            ".....",
            ".....",
            "....."
    );


    public static boolean[][] getMatrix(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'A' -> A;
            case 'B' -> B;
            case 'C' -> C;
            case 'D' -> D;
            case 'E' -> E;
            case 'F' -> F;
            case 'G' -> G;
            case 'H' -> H;
            case 'I' -> I;
            case 'J' -> J;
            case 'K' -> K;
            case 'L' -> L;
            case 'M' -> M;
            case 'N' -> N;
            case 'O' -> O;
            case 'P' -> P;
            case 'Q' -> Q;
            case 'R' -> R;
            case 'S' -> S;
            case 'T' -> T;
            case 'U' -> U;
            case 'V' -> V;
            case 'W' -> W;
            case 'X' -> X;
            case 'Y' -> Y;
            case 'Z' -> Z;
            case ' ' -> SPACE;
            default -> null; // Если символ не поддерживается
        };
    }
}