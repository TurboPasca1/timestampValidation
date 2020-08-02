package org.dep.example.config;

import org.dep.example.pages.DefectlessDummyPage;

public class PageObjectManager {

    private static DefectlessDummyPage defectlessDummyPage;

    public static DefectlessDummyPage getDefectlessDummyPage() {
        return defectlessDummyPage;
    }

    public static void setDefectlessDummyPage(DefectlessDummyPage defectlessDummyPage) {
        PageObjectManager.defectlessDummyPage = defectlessDummyPage;
    }

    public static void resetPageObjects() {
        defectlessDummyPage = new DefectlessDummyPage(WebDriverManager.getWebDriver());
    }
}
