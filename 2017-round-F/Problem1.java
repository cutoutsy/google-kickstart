package codejam;

import java.util.ArrayList;
import java.util.List;

public class Problem1 {

    static boolean flag = true;
    public static List isWorstPivots1(List<Integer> list) {
        if (list.size() <= 1) return list;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        int pivot = list.get((list.size()-1)/2);
        for (int i = 0; i < list.size(); i++) {
            if (i != (list.size()-1)/2) {
                if (list.get(i) <= pivot) {
                    left.add(list.get(i));
                } else {
                    right.add(list.get(i));
                }
            }
        }
        if (left.size() != 0 && right.size() != 0){
            flag = false;
        }
        List<Integer> ans = new ArrayList<>();
        ans.addAll(isWorstPivots1(left));
        ans.add(pivot);
        ans.addAll(isWorstPivots1(right));
        return ans;
    }

    public static boolean isWorstPivots1(int[] nums) {
        int min = 1;
        int max = nums.length;
        int start = (nums.length - 1) / 2;
        int[] move = new int[2];
        for (int i = 0; i <= (nums.length - 1) / 2; i++) {
            if ((nums.length & 1) == 1) {
                move[0] = -1;
                move[1] = 1;
            } else {
                move[0] = 1;
                move[1] = -1;
            }
            for (int j = 0; j < move.length; j++) {
                if ((start + move[j] * i) > -1 || (start + move[j] * i) < nums.length ) {
                    if (nums[start + move[j] * i] == min) {
                        min++;
                    } else if (nums[start + move[j] * i] == max) {
                        max--;
                    } else {
                        return false;
                    }
                    if (i == 0) break; //当i = 0时，即为初始位置，循环一次
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        List<String> data = Utils.readFileToList("./data/A-large.in", "UTF-8");
        int caseIndex = 1;
        for (int i = 2; i < data.size(); i = i + 2) {
            List<Integer> input = new ArrayList<>();
            String[] nums = data.get(i).split(" ");
            for (int j = 0; j < nums.length; j++) {
                input.add(Integer.valueOf(nums[j]));
            }
            isWorstPivots1(input);
            if (flag) {
                System.out.println("Case #" + caseIndex+": YES");
            } else {
                System.out.println("Case #" + caseIndex+": NO");
            }
            caseIndex++;
            flag = true;
            input.clear();
        }
    }
}
