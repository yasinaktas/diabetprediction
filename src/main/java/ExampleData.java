public class ExampleData {
    String gender;
    double age;
    double hypertension;
    double heartDisease;
    String smokingHistory;
    double bmi;
    double hba1cLevel;
    double bloodGlucoseLevel;
    double diabetes;

    public ExampleData(String gender, double age, double hypertension, double heartDisease, String smokingHistory, double bmi, double hba1cLevel, double bloodGlucoseLevel, double diabetes) {
        this.gender = gender;
        this.age = age;
        this.hypertension = hypertension;
        this.heartDisease = heartDisease;
        this.smokingHistory = smokingHistory;
        this.bmi = bmi;
        this.hba1cLevel = hba1cLevel;
        this.bloodGlucoseLevel = bloodGlucoseLevel;
        this.diabetes = diabetes;
    }

    public String getGender() {
        return gender;
    }

    public double getAge() {
        return age;
    }

    public double getHypertension() {
        return hypertension;
    }

    public double getHeartDisease() {
        return heartDisease;
    }

    public String getSmokingHistory() {
        return smokingHistory;
    }

    public double getBmi() {
        return bmi;
    }

    public double getHba1cLevel() {
        return hba1cLevel;
    }

    public double getBloodGlucoseLevel() {
        return bloodGlucoseLevel;
    }

    public double getDiabetes() {
        return diabetes;
    }
}
