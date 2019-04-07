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
        double gcd = calculateGCD((int) numerator, (int) denominator);
        numerator /= gcd;
        denominator /= gcd;
        return numerator / denominator;
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

        LM(factors);
    }

    public void LM(List<Integer> factors) {
        Integer l = 0;
        Integer m = 0;
        List<Integer> L = new ArrayList<Integer>();
        List<Integer> M = new ArrayList<Integer>();

        Integer factorsSize = factors.size() - 1;
        for (int j = 1; j <= Math.abs(factors.get(factorsSize)); j++) {
            if (factors.get(factorsSize) % j == 0) {
                m = factors.get(factorsSize) / j;
                L.add(m);
                L.add(-m);
            }
        }

        for (int j = 1; j <= Math.abs(factors.get(0)); j++) {
            if (factors.get(0) % j == 0) {
                l = factors.get(0) / j;
                M.add(l);
                M.add(-l);
            }
        }
        factorsCandidates(L, M);
    }

    public void factorsCandidates(List<Integer> L, List<Integer> M) {

        List<Double> factorsCandidates = new ArrayList<Double>();
        double factorCandidate;

        for (int i = 0; i < L.size(); i++) {
            for (int j = 0; j < M.size(); j++) {
                factorCandidate = reduce((double) L.get(i), (double) M.get(j));
                factorsCandidates.add(factorCandidate);
            }
        }

        List<Double> listWithoutDuplicates = factorsCandidates.stream()
                .distinct()
                .collect(Collectors.toList());

        setUpCandidates(listWithoutDuplicates);
    }

    private void setUpCandidates(List<Double> listWithoutDuplicates) {
        double summary;
        int i;
        for (int m = 0; m < listWithoutDuplicates.size(); m++) {
            i = 0;
            summary = 0;
            for (int n = highestPower; n >= 0; n--) {
                if (n != 0)
                    summary = summary + factors.get(i) * Math.pow(listWithoutDuplicates.get(m), n);
                else summary = summary + factors.get(i);
                i++;
            }
            if (summary == 0) {
                System.out.println("Pierwiastek " + listWithoutDuplicates.get(m) + " jest " + sprawdzKrotnoscPierwiastka(listWithoutDuplicates.get(m)) + " krotny");
            }
        }
    }

    private int sprawdzKrotnoscPierwiastka(double pierwiastek) {
        int krotnosc = 0;
        double tmp;
        List<Double> factorsTempList = new ArrayList<Double>();
        List<Double> hornerTempList = new ArrayList<Double>();
        hornerTempList.add((double) factors.get(0));
        for (int i = 0; i < factors.size(); i++) {
            factorsTempList.add(Double.valueOf(factors.get(i)));
        }

        do {
            for (int i = 0; i < factors.size() - 1; i++) {
                hornerTempList.add(hornerTempList.get(i) * pierwiastek + factorsTempList.get(i + 1));
            }
            tmp = hornerTempList.get(hornerTempList.size() - 1);
            if (hornerTempList.get(hornerTempList.size() - 1) == 0) krotnosc = krotnosc + 1;
            factorsTempList.clear();
            factorsTempList = ((List) ((ArrayList) hornerTempList).clone());
            hornerTempList.clear();
            hornerTempList.add(factorsTempList.get(0));
        } while (tmp == 0);

        return krotnosc;
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}