package btljava;

import java.math.BigInteger;
import java.util.Random;

public class algorithm extends SHA1 {
    private BigInteger p, q, n, d, e;

    public algorithm() {

    }

    public algorithm(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    //Hàm kiểm tra số nguyên tố
    public boolean isPrime(BigInteger x) {
        if (x.compareTo(BigInteger.valueOf(2)) < 0)
            return false;
        if (x.equals(BigInteger.valueOf(2)))
            return true;
        if (x.compareTo(BigInteger.valueOf(2)) > 0) {
            if (x.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
                return false;
            else {
                for (BigInteger i = BigInteger.valueOf(3); i.compareTo(x.sqrt()) <= 0; i = i.add(BigInteger.valueOf(2))) {
                    if (x.mod(i).equals(BigInteger.ZERO))
                        return false;
                }
            }
        }
        return true;
    }

    //Hàm tính modular multiplicative inverse của a modulo b sử dụng thuật toán Euclidean.
    BigInteger mul_inv(BigInteger a, BigInteger b) {
        BigInteger b0 = b, t, q;
        BigInteger x0 = BigInteger.ZERO, x1 = BigInteger.ONE;
        if (b.equals(BigInteger.ONE)) return BigInteger.ONE;
        while (a.compareTo(BigInteger.ZERO) < 0) a = a.add(b);
        while (a.compareTo(BigInteger.ONE) > 0) {
            q = a.divide(b);
            t = b;
            b = a.mod(b);
            a = t;
            t = x0;
            x0 = x1.subtract(q.multiply(x0));
            x1 = t;
        }
        if (x1.compareTo(BigInteger.ZERO) < 0) x1 = x1.add(b0);
        return x1;
    }

    //Hàm tính x^n mod m bằng phương pháp đệ quy.
    public BigInteger pow(BigInteger x, BigInteger n, BigInteger m) {
        if (n.equals(BigInteger.ZERO)) return BigInteger.ONE;

        BigInteger temp = pow(x, n.divide(BigInteger.valueOf(2)), m);
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) return temp.multiply(temp).mod(m);
        return temp.multiply(temp).mod(m).multiply(x).mod(m);
    }

    public void KeyRSA() {
        this.setN(this.getP().multiply(this.getQ()));
        BigInteger phi = (this.getP().subtract(BigInteger.ONE)).multiply(this.getQ().subtract(BigInteger.ONE));
        Random random = new Random();
        BigInteger r = phi.subtract(BigInteger.valueOf(2)).add(BigInteger.ONE);
        BigInteger randomNumber = new BigInteger(r.bitLength(), random).mod(r).add(BigInteger.valueOf(2));

        while (!isPrime(randomNumber)) {
            randomNumber = new BigInteger(r.bitLength(), random).mod(r).add(BigInteger.valueOf(2));
        }
        this.setE(randomNumber);
        this.setD(mul_inv(getE(), phi));
    }

    public String encrypt(String message, BigInteger D, BigInteger N) {
        BigInteger x = BigInteger.ZERO;
        StringBuilder mahoa = new StringBuilder();
        char[] p = message.toCharArray();
        for (int i = 0; i < p.length; i++) {
            if (p[i] < 58) {
                BigInteger tem = BigInteger.valueOf((int) (p[i]) - 48);
                x = this.pow(tem, D, N);
                mahoa.append(x).append("-");
            } else {
                BigInteger tem = BigInteger.valueOf((int) (p[i]) - 87);
                x = this.pow(tem, D, N);
                mahoa.append(x).append("-");
            }
        }
        mahoa.deleteCharAt(mahoa.length() - 1);
        return mahoa.toString();
    }

    public String decrypt(String message, BigInteger E, BigInteger N) {

        String[] parts = message.split("-");
        StringBuilder giaima = new StringBuilder();
        BigInteger y;
        for (String part : parts) {
            if (part != null && !part.isEmpty()) {
                y = new BigInteger(part);
                BigInteger temp = this.pow(y, E, N);
                if (temp.compareTo(BigInteger.TEN) < 0) {
                    giaima.append(temp);
                } else {
                    giaima.append((char) (temp.intValue() + 87));
                }
            }
        }
        return giaima.toString();
    }
}
