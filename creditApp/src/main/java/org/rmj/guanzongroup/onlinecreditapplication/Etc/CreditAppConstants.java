package org.rmj.guanzongroup.onlinecreditapplication.Etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_CoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_Dependent;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_DisbursementInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_EmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_Finance;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_MeansInfoSelection;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_OtherInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PensionInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PersonalInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_ResidenceInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SelfEmployedInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseEmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpousePensionInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseResidenceInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseSelfEmployedInfo;

public class CreditAppConstants {


    /**
     *            <p>0 new Fragment_PersonalInfo(),</p>
     *            <p>1 new Fragment_ResidenceInfo(),</p>
     *            <p>2 new Fragment_MeansInfoSelection(),</p>
     *            <p>3 new Fragment_EmploymentInfo(),</p>
     *            <p>4 new Fragment_SelfEmployedInfo(),</p>
     *            <p>5 new Fragment_Finance(),</p>
     *            <p>6 new Fragment_PensionInfo(),</p>
     *            <p>7 new Fragment_SpouseInfo(),</p>
     *            <p>8 new Fragment_SpouseResidenceInfo(),</p>
     *            <p>9 new Fragment_SpouseEmploymentInfo(),</p>
     *            <p>10 new Fragment_SpouseSelfEmployedInfo(),</p>
     *            <p>11 new Fragment_SpousePensionInfo(),</p>
     *            <p>12 new Fragment_DisbursementInfo(),</p>
     *            <p>13 new Fragment_Dependent(),</p>
     *            <p>14 new Fragment_OtherInfo(),</p>
     *            <p>15 new Fragment_CoMaker()</p>
     */
    public static Fragment[] APPLICATION_PAGES = {

            new Fragment_PersonalInfo(),
            new Fragment_ResidenceInfo(),
            new Fragment_MeansInfoSelection(),
            new Fragment_EmploymentInfo(),
            new Fragment_SelfEmployedInfo(),
            new Fragment_Finance(),
            new Fragment_PensionInfo(),
            new Fragment_SpouseInfo(),
            new Fragment_SpouseResidenceInfo(),
            new Fragment_SpouseEmploymentInfo(),
            new Fragment_SpouseSelfEmployedInfo(),
            new Fragment_SpousePensionInfo(),
            new Fragment_DisbursementInfo(),
            new Fragment_Dependent(),
            new Fragment_OtherInfo(),
            new Fragment_CoMaker()
    };

    public static String[] APPLICATION_TYPE = {
            "Motorcycle",
            "Sidecar",
            "Others",
            "Mobile Phone",
            "Cars",
            "Services"};

    public static String[] CUSTOMER_TYPE = {
            "Select Customer Type",
            "New",
            "Repeat"};

    public static String[] INSTALLMENT_TERM = {
            "Select Installment Term (Required)",
            "36 Months/3 Years",
            "24 Months/2 Years",
            "18 Months",
            "12 Months/1 Year",
            "6 Months"
    };

    public static String[] MOBILE_NO_TYPE = {"Prepaid", "Postpaid"};

    public static String[] CIVIL_STATUS = {
            "Tap here to select (Required)",
            "Single",
            "Married",
            "Separated",
            "Widowed",
            "Single-Parent",
            "Single-Parent W/ Live-in Partner"};

    public static String[] HOUSEHOLDS ={
            "Select Households (Required)",
            "Living with Family(Spouse, Children)",
            "Living with Family(Father, Mother, Sibling)",
            "Living with Relatives"};

    public static String[] HOUSE_TYPE = {
            "Select House Type (Required)",
            "Concrete",
            "Concrete and Wood",
            "Wood"};

    public static String[] LENGTH_OF_STAY = {"Select Length of Stay (Required)", "Month", "Year"};

    public static String[] BUSINESS_NATURE = {
            "Select Nature of Business (Required)",
            "BPO/Call Center",
            "Business Services",
            "Communication/Transportation",
            "Construction",
            "Financing Banking",
            "Health and Wellness",
            "Hotel/Resort",
            "Insurance",
            "Manufacturing",
            "Mining/Agriculture",
            "Pharmaceutical",
            "Real Estate",
            "Restaurants/Bars/Caffe",
            "Social Services",
            "utilities",
            "WholeSale/Retail",
            "Others"};

    public static String[] GOVERMENT_LEVEL = {
            "Select Government Level (Required)",
            "LGU",
            "Provincial",
            "National"};

    public static String[] COMPANY_LEVEL = {
            "Select Company Level (Required)",
            "Local",
            "National",
            "Multi-National"};

    public static String[] EMPLOYEE_LEVEL = {
            "Select Employee Level (Required)",
            "Rank and file",
            "Supervisory",
            "Managerial"};

    public static String[] OFW_CATEGORY = {
            "Select Work Category (Required)",
            "Household Services",
            "Non-Technical",
            "Skilled/Professional"};

    public static String[] OFW_REGION = {
            "Select Region (Required)",
            "America",
            "Europe",
            "Oceania",
            "Asia",
            "Others"
    };

    public static String[] YEARS_OF_EXPERIENCE = {
            "Select Yeas of Experience (Required)",
            "Below 1 Year",
            "1 Year",
            "2 Years",
            "3 Years",
            "4 Years",
            "5 Years",
            "6 Years",
            "7 Years",
            "8 Years",
            "9 Years",
            "10 Years",
            "More than 10 Years"};

    public static String[] SCHOOL_LEVEL = {
//            "Select School Level (Required)",
            "Primary",
            "High School",
            "Technical/Vocational",
            "College",
            "College Graduate"};

    public static String[] SCHOOL_TYPE = {
//            "Select School Type (Required)",
            "Public",
            "Private"};

    public static String[] EMPLOYMENT_STATUS = {
//            "Select Employment Status (Required)",
            "Regular",
            "Probationary",
            "Contractual",
            "Seasonal"};

    public static String[] BUSINESS_SIZE = {
            "Select Size of Business (Required)",
            "Micro 1 (Less than 10,000 Income/Month)",
            "Micro 2 (Less than 50,000 Income/Month)",
            "Micro 3 (Less than 100,000 Income/Month)",
            "Small (Less than 300,000 Income/Month)",
            "Medium (Less than 1,000,000 Income/Month)",
            "Large (More than 1,000,000 Income/Month)"};


    public static String[] ACCOUNT_TYPE = {
            "Select Account Type",
            "Checkings",
            "Savings",
            "Payroll"};

    public static String[] EMPLOYMENT_SECTOR = {
//            "Select Employment Type",
            "Private",
            "Government",
            "OFW"};
    public static String[] STUDENT_YES_NO = {
            "Select student status",
            "Yes",
            "No"};
    public static String[] EMPLOYED_YES_NO = {
            "Select employment status",
            "Yes",
            "No"};


    public static String[] DEPENDENT_RELATIONSHIP = {
//            "Select Relationship",
            "Child",
            "Parents",
            "Siblings",
            "Relatives"};
//            "Others"};

    public static String[] UNIT_USER = {
            "Select Unit User",
            "Principal Customer",
            "Others"};

    public static String[] UNIT_USER_OTHERS = {
            "Select Other User",
            "Children",
            "Parents",
            "Siblings",
            "Relatives",
            "Others",
            "Spouse"};

    public static String[] UNIT_PURPOSE = {
            "Select Unit Purpose",
            "Business",
            "Personal Service",
            "Raffle",
            "Gift"};

    public static String[] UNIT_PAYER = {
            "Select other payer",
            "Children",
            "Parents",
            "Siblings",
            "Relatives",
            "Spouse"};

    public static  String[] UNIT_PAYER_NO_SPOUSE = {
            "Select other payer",
            "Children",
            "Parents",
            "Siblings",
            "Relatives"};
    public static String[] INTO_US = {
            "Select company information source",
            "Facebook",
            "Branch",
            "Friends",
            "Relatives",
            "Walk-In",
            "Others"};

    public static String[] INTO_US_NO_SPOUSE= {
            "Select company information source",
            "Facebook",
            "Branch",
            "Friends",
            "Relatives",
            "Walk-In",
            "Others"};

    public static String[] CO_MAKER_RELATIONSHIP= {
            "Children",
            "Parents",
            "Siblings",
            "Relatives",
            "Other",
            "Spouse"};
    public static String[] CO_MAKER_INCOME_SOURCE= {
            "Employed",
            "Self-Employed",
            "With-Financer",
            "Pensioner"};
}
