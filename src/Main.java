import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    List<Integer> factors = new ArrayList<Integer>();
    Integer highestPower = 0;

    public Main() {
        getPolynomial();
    }

    public double calculateGCD(int numerator, int denominator) {
        if (numerator % denominator == 0) {
            return denominator;
        }
        return calculateGCD(denominator, numerator % denominator);
    }

    double reduce(double numerator, double denominator) {
        double gcd = calculateGCD((int)numerator, (int)denominator);
        numerator /= gcd;
        denominator /= gcd;
        return numerator/denominator;
    }

    private void getPolynomial() {
        Scanner scanner = new Scanner(System.in);
        Integer a = 0;

        System.out.println("Wprowadź najwyższą potęgę wielomianu: ");
        highestPower = scanner.nextInt();
        System.out.println("Wprowadź " + highestPower + " współczynników wielomianu");

        for (int i = 0; i <= highestPower; i++) {
            a = scanner.nextInt();
            factors.add(i, a);
        }

        /*for(int i = 0; i < highestPower; i++){
            System.out.println(factors.get(i));
        }*/

        LM(factors);
    }

    public void LM(List<Integer> factors) {
        Integer l = 0;
        Integer m = 0;
        List<Integer> L = new ArrayList<Integer>();
        List<Integer> M = new ArrayList<Integer>();

        Integer factorsSize = factors.size() - 1;
        // System.out.println(factorsSize);
        for (int j = 1; j <= Math.abs(factors.get(factorsSize)); j++) {
            if (factors.get(factorsSize) % j == 0) {
                //System.out.println("dzielnik wpolczynnika przy max potędze"+factors.get(factorsSize)/j);
                m = factors.get(factorsSize) / j;
                L.add(m);
                L.add(-m);
            }
        }

        for (int j = 1; j <= Math.abs(factors.get(0)); j++) {
            //System.out.println(factors.get(i)/j);
            if (factors.get(0) % j == 0) {
                //System.out.println("dzielnik wyrazu wolnego"+factors.get(0)/j);
                l = factors.get(0) / j;
                M.add(l);
                M.add(-l);
            }
        }
        factorsCandidates(L, M);
    }

    public void factorsCandidates(List<Integer> L, List<Integer> M) {
        System.out.println(L);
        System.out.println(M);

        List<Double> factorsCandidates = new ArrayList<Double>();
        double factorCandidate;

        for (int i = 0; i < L.size(); i++) {
            for (int j = 0; j < M.size(); j++) {
                factorCandidate = reduce((double) L.get(i), (double) M.get(j));
                factorsCandidates.add(factorCandidate);
            }
        }

        //System.out.println(factorsCandidates);
        List<Double> listWithoutDuplicates = factorsCandidates.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(listWithoutDuplicates);

        setUpCandidates(listWithoutDuplicates);
    }

    private void setUpCandidates(List<Double> listWithoutDuplicates) {
        double summary;
        int i;
        for (int m = 0; m < listWithoutDuplicates.size(); m++) {
            i = 0;
            summary = 0;
            for (int n = highestPower; n >= 0; n--) {
            /*System.out.println(factors.get(i));
            System.out.println(factors.get(i));
            System.out.println(factors.get(i)*Math.pow(listWithoutDuplicates.get(1), n));*/
                if (n != 0)
                    summary = summary + factors.get(i) * Math.pow(listWithoutDuplicates.get(m), n);
                else summary = summary + factors.get(i);

                //System.out.println(summary);
                i++;
            }
            System.out.println("SUMMARY FOR VALUE " + listWithoutDuplicates.get(m) + ": " + summary);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}