import java.util.Arrays;

public class HomeWork5_2 {  //  на 2 потока
    final static int SIZE = 100000000;
    public static void main(String[] args){
        wholeArray();
        dividedArray();

    }
    public static void wholeArray(){
        float[] array = new float[SIZE];
        Arrays.fill(array, 1f);  // метод для заполнения массива единицами
        long t = System.currentTimeMillis();
        calculate(array, 0);
        System.out.println("Whole Array: " + (System.currentTimeMillis() - t));
    }
    public static void dividedArray(){
        float[] array = new float[SIZE];
        Arrays.fill(array, 1);
        int half = SIZE/2;

        float[] firstHalf = new float[half];
        float[] secondHalf  = new float[half];

        long t = System.currentTimeMillis();
        System.arraycopy(array, 0, firstHalf, 0, half);
        System.arraycopy(array, half, secondHalf, 0, half);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run(){
                calculate(firstHalf, 0);
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run(){
                calculate(secondHalf, half);
            }
        });
        try{
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.arraycopy(firstHalf, 0, array, 0, half);
        System.arraycopy(secondHalf, 0, array, half, half);
        System.out.println("Divided Array: " + (System.currentTimeMillis() - t));
    }

    public static void calculate(float[] array, int offset){ //offset - задаем смещение
        for(int i = 0; i < array.length; i++){
            int j = offset + i;
            array[i] = (float) (array[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
        }
    }
}
