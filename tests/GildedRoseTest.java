import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    private void assertItemsAreEqual(Item item, Item otherItem) {
        assertTrue(item.name.equals(otherItem.name));
        assertTrue(item.quality == otherItem.quality);
        assertTrue(item.sellIn == otherItem.sellIn);
    }

    private void makeDaysPass(int numberOfDays) {
        for (int i = 0; i < numberOfDays; ++i) {
            GildedRose.updateQuality();
        }
    }

    private void assertItemsQuality(int quality, Item item) {
        assertEquals(quality, item.getQuality());
    }

    @Test
    public void addingItemsToList() {
        Item agedBrie = new Item("Aged Brie", 2, 0);
        GildedRose.addItem(agedBrie);

        assertEquals(1, GildedRose.itemsSize());
        assertItemsAreEqual(agedBrie, GildedRose.getItemAtIndex(0));
    }

    @Test
    public void sellInWorkings() {
        Item whatever = new Item("whatever", 2, 0);
        GildedRose.addItem(whatever);

        makeDaysPass(10);

        assertEquals(-8, whatever.getSellIn());
    }

    @Test
    public void agedBrieQualityIncreasesByOneEachDayBeforeSellDateAndByTwoFromSellDateOn() {
        Item agedBrie = new Item("Aged Brie", 2, 0);
        GildedRose.addItem(agedBrie);

        makeDaysPass(4);

        assertItemsQuality(6, agedBrie);
    }

    @Test
    public void qualityCannotBeMoreThanFifty() {
        Item agedBrie = new Item("Aged Brie", 2, 0);
        GildedRose.addItem(agedBrie);

        makeDaysPass(300);

        assertItemsQuality(50, agedBrie);
    }

    @Test
    public void sulfurasIsInmutable() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        GildedRose.addItem(sulfuras);

        makeDaysPass(10);

        assertItemsQuality(80, sulfuras);
    }

    @Test
    public void backstagePassesQualityIncreasesByOneEachDayBeforeTenDaysFromSellDate() {
        Item backstagePasses = new Item(
                "Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose.addItem(backstagePasses);

        makeDaysPass(5);

        assertItemsQuality(25, backstagePasses);
    }

    @Test
    public void backstagePassesQualityIncreasesByTwoEachDayBetweenTenAndFiveDaysBeforeSellDate() {
        Item backstagePasses = new Item(
                "Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose.addItem(backstagePasses);

        makeDaysPass(10);

        assertItemsQuality(35, backstagePasses);
    }

    @Test
    public void backstagePassesQualityIncreasesByThreeEachDayBetweenFiveDaysFromSellDateAndSellDate() {
        Item backstagePasses = new Item(
                "Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose.addItem(backstagePasses);

        makeDaysPass(15);

        assertItemsQuality(50, backstagePasses);
    }

    @Test
    public void backstagePassesQualityIsZeroAfterTheSellDate() {
        Item backstagePasses = new Item(
                "Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose.addItem(backstagePasses);

        makeDaysPass(16);

        assertItemsQuality(0, backstagePasses);
    }

    @Test
    public void regularItemsQualityDecreasesByOneEachDayBeforeSellDate() {
        Item regularItem = new Item("+5 Dexterity Vest", 10, 20);
        GildedRose.addItem(regularItem);

        makeDaysPass(10);

        assertItemsQuality(10, regularItem);
    }

    @Test
    public void regularItemsQualityDecreasesByTwoEachDayAfterSellDate() {
        Item regularItem = new Item("+5 Dexterity Vest", 10, 20);
        GildedRose.addItem(regularItem);

        makeDaysPass(15);

        assertItemsQuality(0, regularItem);
    }

    @Test
    public void regularItemsQualityCannotBeLessThanZero() {
        Item regularItem = new Item("+5 Dexterity Vest", 10, 20);
        GildedRose.addItem(regularItem);

        makeDaysPass(200);

        assertItemsQuality(0, regularItem);
    }

    @Test
    public void conjuredItemsQualityDecreasesByTwoEachDayBeforeSellDate() {
        Item conjuredItem = new Item("Conjured Mana Cake", 3, 6);
        GildedRose.addItem(conjuredItem);

        makeDaysPass(3);

        assertItemsQuality(0, conjuredItem);
    }

    @Test
    public void conjuredItemsQualityDecreasesByFourEachDayAfterSellDate() {
        Item conjuredItem = new Item("Conjured Mana Cake", 5, 18);
        GildedRose.addItem(conjuredItem);

        makeDaysPass(7);

        assertItemsQuality(0, conjuredItem);
    }

    @Test
    public void conjuredItemsQualityCannotBeLessThanZero() {
        Item conjuredItem = new Item("Conjured Mana Cake", 5, 18);
        GildedRose.addItem(conjuredItem);

        makeDaysPass(200);

        assertItemsQuality(0, conjuredItem);
    }
}