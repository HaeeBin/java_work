import java.util.Arrays;
import java.util.Scanner;

public class Bucket {
	static int[] num;
	static int minimum;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        //n개의 숫자를 a개의 bucket에 담아 최소 오차 구하기
        int count = sc.nextInt(); //숫자 개수
        int bucketSize = sc.nextInt(); //bucket 개수
        num = new int[count];
        boolean[] visited = new boolean[count];
        
        for(int i = 0; i < count; i++)
        	num[i] = sc.nextInt();
        
        minimum = 987654321;
        Arrays.sort(num);
        for(int i = 0; i < count; i++) {
        	perm(num, visited, 0, count, bucketSize - 1, bucketSize);
        } 
        System.out.println(minimum);

	}
	public static void perm(int[] num, boolean[] visited, int start, int n, int k, int bucketSize) {
		if(k == 0) {
			calc(n, bucketSize, num, visited);
			return;
		}
		
		for(int i = start; i < n; i++) {
			visited[i] = true;
			perm(num, visited, i + 1, n, k - 1, bucketSize);
			visited[i] = false;
		}
	}
	public static void calc(int count, int bucketSize, int[] num, boolean[] visited) {
		int k;
		int p1 = 0, p2 = 0;
		int result = 0;
		int numSize = 0;
		int[] sum = new int[bucketSize];
		double[] arr = new double[bucketSize];
		double[] error = new double[bucketSize];
		for(int i = 0; i < bucketSize; i++) {
			sum[i] = 0;
			arr[i] = 0;
			error[i] = 0;
		}
		k = 0;
		int i;
		for(i = 0; i < num.length; i++) {
			sum[k] += num[i];
			numSize = numSize + 1;
			if(visited[i]) {
				arr[k] = (double) sum[k] / (double)numSize;
				k = k + 1;
				numSize = 0;
			}
			if(i == num.length - 1 && visited[num.length - 1] == false) {
				if(numSize != 0)
					arr[k] = (double) sum[k] / (double)numSize;
				else
					arr[k] = num[i];
			}
		}
		k = 0;
		for(i = 0; i < count; i++) {
			if((arr[k] - (int)arr[k]) * 10 != 0.0) {
				p1 += ((int)arr[k] - num[i]) * ((int)arr[k] - num[i]);
			    p2 += ((int)arr[k] + 1 - num[i]) * ((int)arr[k] + 1 - num[i]);
				if(visited[i]) {
					error[k] = Math.min(p1,  p2);
					k = k + 1;
					p1 = 0;
					p2 = 0;
				}
				if(i == count - 1 && p1 != 0 && visited[num.length - 1] == false)
					error[k] = Math.min(p1,  p2);
			}
			else {
				error[k] += (arr[k] - num[i]) * (arr[k] - num[i]);
				if(visited[i])
					k = k + 1;
			}
		} 
		for(i = 0; i < error.length; i++)
			result += error[i];
		minimum = Math.min(result, minimum);
	}
}
