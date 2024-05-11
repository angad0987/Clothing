package com.example.clothing.Utilities;

public class PriceRange {
    private int minRange;
    private int maxRange;

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public static PriceRange getRange(String rangeString) {
        PriceRange range = new PriceRange();
        switch (rangeString) {
            case "under_300":
                range.setMinRange(0);
                range.setMaxRange(300);
                break;

            case "300_to_600":
                range.setMinRange(300);
                range.setMaxRange(600);
                break;
            case "600_to_1200":
                range.setMinRange(600);
                range.setMaxRange(1200);
                break;

            case "1200_to_1600":
                range.setMinRange(0);
                range.setMaxRange(300);
                break;

            case "over_1600":
                range.setMinRange(1600);
                range.setMaxRange(Integer.MAX_VALUE);
                break;

            default:
                range.setMinRange(0);
                range.setMaxRange(Integer.MAX_VALUE);
                break;

        }
        return range;
    }

}
