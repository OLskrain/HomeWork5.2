import java.util.Arrays;

public class HomeWork5_3 { //Универсальный метод на n-потоков(чтное)
    final static int SIZE = 30000000;
    final static int THREAD_COUNT = 12; //количество потоков
    final static int PART_SIZE = SIZE/THREAD_COUNT; //часть массива. размер зависит от числа потоков
    public static void main(String[] args){
        float[] array = new float[SIZE];
        Arrays.fill(array, 1);

        long t = System.currentTimeMillis();
        float[][] m = new float[THREAD_COUNT][PART_SIZE];
        Thread[] threads = new Thread[THREAD_COUNT]; //массив потоков
        for(int i = 0; i < threads.length; i++){
            System.arraycopy(array, PART_SIZE*i, m[i], 0, PART_SIZE);
            final int u = i;
            threads[i] = new Thread(new Runnable() { //создаем потоки
                @Override
                public void run(){
                    for(int j = 0, z = u*PART_SIZE; j < PART_SIZE; j++, z++){
                        m[u][j] = (float) (m[u][j] * Math.sin(0.2f + z / 5) * Math.cos(0.2f + z / 5) * Math.cos(0.4f + z / 2));
                    }
                }
            });
            threads[i].start();
        }
        for(int i = 0; i < THREAD_COUNT; i++){  //joinим все потоки
            try{
                threads[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        for(int i = 0; i < THREAD_COUNT; i++){
            System.arraycopy(m[i], 0, array, i*PART_SIZE, PART_SIZE);
        }
        System.out.println("TIME: " + (System.currentTimeMillis() - t));
    }
}
