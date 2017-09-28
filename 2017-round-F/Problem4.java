package codejam;

import java.util.Arrays;
import java.util.List;

public class Problem4 {
    
    public boolean[] alreadyComputed;
    public int[] dp;

    public Problem4() {
        this.alreadyComputed = new boolean[10000 + 1];
        this.dp = new int[10000 + 1];
        Arrays.fill(alreadyComputed, false);
    }

    public static int findCakeNumsByArea(int area) {
        if (area == 0) return 0;
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= Math.sqrt(area); i++) {
            ans = Math.min(ans, findCakeNumsByArea(area - i * i) + 1);
        }
        return ans;
    }

    // 动态规划
    public int findCakeNumsByAreaDp(int inputArea) {
        if (inputArea == 0) return 0;
        if (alreadyComputed[inputArea]) return dp[inputArea];
        dp[inputArea] = Integer.MAX_VALUE;
        alreadyComputed[inputArea] = true;
        for (int i = 1; i <= Math.sqrt(inputArea); i++) {
            dp[inputArea] = Math.min(dp[inputArea], findCakeNumsByAreaDp(inputArea - i * i) + 1);
        }
        return dp[inputArea];
    }

    // 根据四平方和定理
    public static int findCakeNumsByAreaWithLargeDataSet(int area) {
        for (int i = 1; i <= Math.sqrt(area); i++) {
            if(i * i == area) return 1;
        }
        for (int i = 1; i <= Math.sqrt(area); i++) {
            for (int j = i; j <= Math.sqrt(area); j++) {
                if (i * i + j * j == area) return 2;
            }
        }
        for (int i = 1; i <= Math.sqrt(area); i++) {
            for (int j = i; j <= Math.sqrt(area); j++) {
                for (int k = j; k <= Math.sqrt(area); k++) {
                    if (i * i + j * j + k *k == area) return 3;
                }
            }
        }
        return 4;
    }

    public static void main(String[] args) {
        List<String> data = Utils.readFileToList("./data/D-large-practice.in", "UTF-8");
        int caseIndex = 1;

        Problem4 problem4 = new Problem4();
        for (int i = 1; i <= 10000; i++) {
            problem4.findCakeNumsByAreaDp(i);
        }

        for (int i = 1; i < data.size(); i++) {
            int ans = problem4.findCakeNumsByAreaDp(Integer.valueOf(data.get(i)));
            System.out.println("Case #" + caseIndex+": " + ans);
            caseIndex++;
        }
    }
}
