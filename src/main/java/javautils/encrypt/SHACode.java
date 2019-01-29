 package javautils.encrypt;

 public class SHACode { private static final long SH0 = 1732584193L;
   private static final long SH1 = 4023233417L;
   private static final long SH2 = 2562383102L;
   private static final long SH3 = 271733878L;
   private static final long SH4 = 3285377520L;
   private static final long K0 = 1518500249L;
   private static final long K1 = 1859775393L;
   private static final long K2 = 2400959708L;
   private static final long K3 = 3395469782L;
   private static long[] W;
   private static byte[] BW;
   private static int p0;
   private static int p1;
   private static int p2;
   private static int p3;
   private static int p4;
   private static long A;
   private static long B;
   private static long C;
   private static long D;
   private static long E;
   private static long temp;

   private static long f0(long x, long y, long z) { return z ^ x & (y ^ z); }

   private static long f1(long x, long y, long z)
   {
     return x ^ y ^ z;
   }

   private static long f2(long x, long y, long z) {
     return x & y | z & (x | y);
   }

   private static long f3(long x, long y, long z) {
     return x ^ y ^ z;
   }

   private static long S(int n, long X) {
     long tem = 0L;
//     X &= 0xFFFFFFFFFFFFFFFF;
    X &= 0xFFFFFFFF;

     if (n == 1) {
       tem = X >> 31 & 1L;
     }

     if (n == 5)
       tem = X >> 27 & 0x1F;
     if (n == 30) {
       tem = X >> 2 & 0x3FFFFFFF;
     }
     return X << n | tem;
   }


   private static void r0(int f, long K)
   {
     temp = S(5, A) + f0(B, C, D) + E + W[(p0++)] + K;
     E = D;
     D = C;
     C = S(30, B);
     B = A;
     A = temp;
   }

   private static void r1(int f, long K)
   {
     temp = W[(p1++)] ^ W[(p2++)] ^ W[(p3++)] ^ W[(p4++)];

     switch (f) {
     case 0:
       temp = S(5, A) + f0(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
       break;
     case 1:
       temp = S(5, A) + f1(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
       break;
     case 2:
       temp = S(5, A) + f2(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
       break;
     case 3:
       temp = S(5, A) + f3(B, C, D) + E + (W[(p0++)] = S(1, temp)) + K;
     }


     E = D;
     D = C;
     C = S(30, B);
     B = A;
     A = temp;
   }

   public static long getCode(String mem)
   {
     int length = mem.toCharArray().length;



     int sp = 0;

     W = new long[80];
     BW = new byte['ŀ'];
     int padded = 0;
     char[] s = mem.toCharArray();
     long h0 = 1732584193L;
     long h1 = 4023233417L;
     long h2 = 2562383102L;
     long h3 = 271733878L;
     long h4 = 3285377520L;
     int lo_length ; int hi_length = lo_length = 0;
//     for (;;) { int nread; int nread; if (length < 64) {
    for (;;) { int nread; ; if (length < 64) {
         nread = length;
       } else
         nread = 64;
       length -= nread;
       for (int m = 0; m < nread; m++)
         BW[m] = ((byte)s[(sp++)]);
       if (nread < 64) {
         int nbits = nread * 8;

//         if (lo_length += nbits < nbits)
            if ((lo_length += nbits) < nbits)
           hi_length++;
         if ((nread < 64) && (padded == 0)) {
           BW[(nread++)] = Byte.MIN_VALUE;
           padded = 1;
         }

         for (int i = nread; i < 64; i++) {
           BW[i] = 0;
         }
         byte[] tar = new byte[4];
         for (int z = 0; z < 64; z += 4) {
           for (int y = 0; y < 4; y++) {
             tar[y] = BW[(z + y)];
           }
           W[(z >> 2)] = 0L;
           W[(z >> 2)] = ((W[(z >> 2)] | tar[0]) << 8);
           W[(z >> 2)] = ((W[(z >> 2)] | tar[1] & 0xFF) << 8);
           W[(z >> 2)] = ((W[(z >> 2)] | tar[2] & 0xFF) << 8 | tar[3] & 0xFF);
         }


         if (nread <= 56) {
           W[14] = hi_length;
           W[15] = lo_length;
         }
       } else {
         lo_length += 512; if (lo_length < 512) {
           hi_length++;
         }
         byte[] tar = new byte[4];
         for (int z = 0; z < 64; z += 4) {
           for (int y = 0; y < 4; y++) {
             tar[y] = BW[(z + y)];
           }
           W[(z >> 2)] = 0L;
           W[(z >> 2)] = ((W[(z >> 4)] | tar[0]) << 8);
           W[(z >> 2)] = ((W[(z >> 2)] | tar[1] & 0xFF) << 8);
           W[(z >> 2)] = ((W[(z >> 2)] | tar[2] & 0xFF) << 8 | tar[3] & 0xFF);
         }
       }

       p0 = 0;
       A = h0;
       B = h1;
       C = h2;
       D = h3;
       E = h4;

       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);
       r0(0, 1518500249L);

       p1 = 13;
       p2 = 8;
       p3 = 2;
       p4 = 0;
       r1(0, 1518500249L);
       r1(0, 1518500249L);
       r1(0, 1518500249L);
       r1(0, 1518500249L);

       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);
       r1(1, 1859775393L);

       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);
       r1(2, 2400959708L);

       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);
       r1(3, 3395469782L);

       h0 += A;
       h1 += B;
       h2 += C;
       h3 += D;
       h4 += E;


       if (nread <= 56) {
         break;
       }
     }
     W = null;
     BW = null;
     s = null;
     A = 0L;
     B = 0L;
     C = 0L;
     D = 0L;
     E = 0L;
     temp = 0L;
     h4 = 0L;
     h3 = 0L;
     h2 = 0L;
     h1 = 0L;
     p0 = 0;
     p1 = 0;
     p2 = 0;
     p3 = 0;
     p4 = 0;



     int lowBit = (int)(h0 & 0xFFFF);
     int highBit = (int)(h0 >> 16 & 0xFFFFF);
     h0 = 0L;
     String shaHigh = new String(Integer.toHexString(highBit));

     int leng = shaHigh.length();
     if (leng > 4) {
       shaHigh = shaHigh.substring(leng - 4);
     }
     else if (leng < 4) {
       for (int m = 0; m < 4 - leng; m++) {
         shaHigh = "0" + shaHigh;
       }
     }
     String shaLow = new String(Integer.toHexString(lowBit));

     leng = shaLow.length();
     if (leng > 4) {
       shaLow = shaLow.substring(leng - 4);
     }
     else if (leng < 4) {
       for (int m = 0; m < 4 - leng; m++)
         shaLow = "0" + shaLow;
     }
     return Long.parseLong(shaHigh + shaLow, 16);
   }
 }


/* Location:              /Users/vincent/Downloads/至尊程序/lotteryCapture/lotteryCaptureRepair.jar!/javautils/encrypt/SHACode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */