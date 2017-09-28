package codejam;

import java.util.Arrays;
import java.util.List;

public class Problem2 {
    public static int findMaxHonor(int energy, int[] skills) {
        int honor = 0;
        Arrays.sort(skills);
        int size = skills.length - 1;
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != 0) {
                if (energy > skills[i] && energy - skills[i] > 0) {
                    energy -= skills[i];
                    honor++;
                    skills[i] = 0;
                } else if (energy <= skills[i] && honor > 0 && size > i) {
                    energy += skills[size];
                    honor--;
                    skills[size] = 0;
                    size--;
                    i--;
                }
            }
        }
        return honor;
    }

    public static void main(String[] args) {
        List<String> data = Utils.readFileToList("./data/B-large.in", "UTF-8");
        int caseIndex = 1;
        for (int i = 1; i < data.size(); i = i + 2) {
            int energy = Integer.valueOf(data.get(i).split(" ")[0]);
            String[] line = data.get(i+1).split(" ");
            int[] skills = new int[line.length];
            for (int j = 0; j < line.length; j++) {
                skills[j] = Integer.valueOf(line[j]);
            }
            int ans = findMaxHonor(energy, skills);
            System.out.println("Case #" + caseIndex + ": " + ans);
            caseIndex++;
        }
    }
}
