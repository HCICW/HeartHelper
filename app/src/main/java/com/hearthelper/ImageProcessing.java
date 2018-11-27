package com.hearthelper;

public class ImageProcessing {

    private static int PtoRedSum(byte[] prs, int width,int height) {
        if (prs == null)
            return 0;
        final int frameSize = width * height;
        int sum = 0;
        for (int j = 0, yp = 0; j < height; j++) {
            int up = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) prs[yp])) - 16;
                if (y < 0)
                    y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & prs[up++]) - 128;
                    u = (0xff & prs[up++]) - 128;
                }
                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0)
                    r = 0;
                else if (r > 262143)
                    r = 262143;
                if (g < 0)
                    g = 0;
                else if (g > 262143)
                    g = 262143;
                if (b < 0)
                    b = 0;
                else if (b > 262143)
                    b = 262143;

                int pixel = 0xff000000 | ((r << 6) & 0xff0000)
                        | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
                int red = (pixel >> 16) & 0xff;
                sum += red;
            }
        }
        return sum;
    }

    public static int PtoRedAvg(byte[] pra, int width,
                                             int height) {
        if (pra == null)
            return 0;
        final int frameSize = width * height;
        int sum = PtoRedSum(pra, width, height);
        return (sum / frameSize);
    }

}
