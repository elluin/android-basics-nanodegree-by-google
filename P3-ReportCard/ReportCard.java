/**
 * The ReportCard stores five numeric values in percentage (from 1 to 100).
 * This represent the result of student in the following subjects:
 * Literature, Math, History, English language and IT skills.
 * The grade is calculated from these percentages like this:
 * if the result is  0 -  24 = 1 (these are the Grades)
 *                  25 -  39 = 2
 *                  40 -  59 = 3
 *                  60 -  79 = 4
 *                  80 - 100 = 5
 */

public class ReportCard {

    //these are the limits of grades
    private static final int LIMIT_OF_2 = 25;
    private static final int LIMIT_OF_3 = 40;
    private static final int LIMIT_OF_4 = 60;
    private static final int LIMIT_OF_5 = 80;

    private int mPercentageLiterature;
    private int mPercentageMath;
    private int mPercentageHistory;
    private int mPercentageEnglish;
    private int mPercentageITSkills;

    /**
     * Constructor create a new ReportCard object.
     *
     * @param percentageLiterature is the percent for Literature
     * @param percentageMath       is the percentage for Math
     * @param percentageHistory    is the percentage for History
     * @param percentageEnglish    is the percentage for English language
     * @param percentageITSkills   is the percentage for IT Skills
     */

    public ReportCard(int percentageLiterature, int percentageMath, int percentageHistory,
                      int percentageEnglish, int percentageITSkills) {
        this.mPercentageLiterature = percentageLiterature;
        this.mPercentageMath = percentageMath;
        this.mPercentageHistory = percentageHistory;
        this.mPercentageEnglish = percentageEnglish;
        this.mPercentageITSkills = percentageITSkills;
    }

    //Get the percentages
    public int getmPercentageLiterature() {
        return mPercentageLiterature;
    }

    public int getmPercentageMath() {
        return mPercentageMath;
    }

    public int getmPercentageHistory() {
        return mPercentageHistory;
    }

    public int getmPercentageEnglish() {
        return mPercentageEnglish;
    }

    public int getmPercentageITSkills() {
        return mPercentageITSkills;
    }

    //Set the percentages
    public void setmPercentageLiterature(int percentageLiterature) {
        this.mPercentageLiterature = percentageLiterature;
    }

    public void setmPercentageMath(int percentageMath) {
        this.mPercentageMath = percentageMath;
    }

    public void setmPercentageHistory(int percentageHistory) {
        this.mPercentageHistory = percentageHistory;
    }

    public void setmPercentageEnglish(int percentageEnglish) {
        this.mPercentageEnglish = percentageEnglish;
    }

    public void setmPercentageITSkills(int percentageITSkills) {
        this.mPercentageITSkills = percentageITSkills;
    }

    //The Grade based on rating table
    public int getGrade(int percentage) {
        if (percentage < LIMIT_OF_2)
            return 1;
        else if (percentage < LIMIT_OF_3)
            return 2;
        else if (percentage < LIMIT_OF_4)
            return 3;
        else if (percentage < LIMIT_OF_5)
            return 4;
        else
            return 5;
    }

    @Override
    public String toString() {
        return "Report Card:" +
                "\n  Literature Percentage:\t" + mPercentageLiterature + "%" + " (" + getGrade(mPercentageLiterature) + ")" +
                "\n  Math Percentage:\t" + mPercentageMath + "%" + " (" + " (" + getGrade(mPercentageMath) + ")" +
                "\n  History Percentage:\t" + mPercentageHistory + "%" + " (" + " (" + getGrade(mPercentageHistory) + ")" +
                "\n  English Language Percentage:\t" + mPercentageEnglish + "%" + " (" + " (" + getGrade(mPercentageEnglish) + ")" +
                "\n  IT Skills Percentage:\t" + mPercentageITSkills + "%" + " (" + " (" + getGrade(mPercentageITSkills) + ")";
    }

}