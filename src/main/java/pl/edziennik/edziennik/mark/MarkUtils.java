package pl.edziennik.edziennik.mark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class MarkUtils {
    public BigDecimal countAverageOfMarks(List<Mark> marks){
        BigDecimal markSum = new BigDecimal(0);
        int weightSum = 0;
        for(Mark mark : marks){
            markSum = markSum.add(mark.getMark().multiply(new BigDecimal(mark.getMarkCategory().getWeight())));
            weightSum += mark.getMarkCategory().getWeight();
        }
        if(weightSum == 0){
            return new BigDecimal(0);
        }
        return markSum.divide(new BigDecimal(weightSum), RoundingMode.DOWN).setScale(2, RoundingMode.DOWN);
    }
}
