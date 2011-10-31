/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.logic.manageRegistrants;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftForm;
import com.shroggle.presentation.site.RegisteredVisitorInfo;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ManageRegistrantsSorterTest {

    final ManageRegistrantsSorter sorter = new ManageRegistrantsSorter();

    @Test
    public void testExecute_SORT_FIRST_NAME_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();
        
        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.FIRST_NAME, false);
        Assert.assertEquals("Anatoly", sortedRegistrants.get(0).getFirstName());
        Assert.assertEquals("Artem", sortedRegistrants.get(1).getFirstName());
        Assert.assertEquals("Dmitry", sortedRegistrants.get(2).getFirstName());
    }
    
    @Test
    public void testExecute_SORT_FIRST_NAME_DESC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.FIRST_NAME, true);
        Assert.assertEquals("Dmitry", sortedRegistrants.get(0).getFirstName());
        Assert.assertEquals("Artem", sortedRegistrants.get(1).getFirstName());
        Assert.assertEquals("Anatoly", sortedRegistrants.get(2).getFirstName());
    }

    @Test
    public void testExecute_SORT_LAST_NAME_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.LAST_NAME, false);
        Assert.assertEquals("Balakirev", sortedRegistrants.get(0).getLastName());
        Assert.assertEquals("Solomadin", sortedRegistrants.get(1).getLastName());
        Assert.assertEquals("Stasuk", sortedRegistrants.get(2).getLastName());
    }

    @Test
    public void testExecute_SORT_EMAIL_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.EMAIL, false);
        Assert.assertEquals("anatoly.balakirev@gmail.com", sortedRegistrants.get(0).getEmail());
        Assert.assertEquals("artem.stasuk@gmail.com", sortedRegistrants.get(1).getEmail());
        Assert.assertEquals("dmitry.solomadin@gmail.com", sortedRegistrants.get(2).getEmail());
    }

    @Test
    public void testExecute_SORT_CREATED_DATE_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.CREATED_DATE, false);
        final Calendar checkCalendar = new GregorianCalendar();
        checkCalendar.setTime(sortedRegistrants.get(0).getCreatedDateRaw());
        Assert.assertEquals(1988, checkCalendar.get(Calendar.YEAR));
        Assert.assertEquals(11, checkCalendar.get(Calendar.MONTH));
        Assert.assertEquals(17, checkCalendar.get(Calendar.DAY_OF_MONTH));

        checkCalendar.setTime(sortedRegistrants.get(1).getCreatedDateRaw());
        Assert.assertEquals(2010, checkCalendar.get(Calendar.YEAR));
        Assert.assertEquals(10, checkCalendar.get(Calendar.MONTH));
        Assert.assertEquals(16, checkCalendar.get(Calendar.DAY_OF_MONTH));

        checkCalendar.setTime(sortedRegistrants.get(2).getCreatedDateRaw());
        Assert.assertEquals(2011, checkCalendar.get(Calendar.YEAR));
        Assert.assertEquals(9, checkCalendar.get(Calendar.MONTH));
        Assert.assertEquals(15, checkCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testExecute_SORT_UPDATED_DATE_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.UPDATED_DATE, false);
        final Calendar checkCalendar = new GregorianCalendar();
        checkCalendar.setTime(sortedRegistrants.get(0).getCreatedDateRaw());
        Assert.assertEquals(1988, checkCalendar.get(Calendar.YEAR));
        Assert.assertEquals(11, checkCalendar.get(Calendar.MONTH));
        Assert.assertEquals(17, checkCalendar.get(Calendar.DAY_OF_MONTH));

        checkCalendar.setTime(sortedRegistrants.get(1).getCreatedDateRaw());
        Assert.assertEquals(2010, checkCalendar.get(Calendar.YEAR));
        Assert.assertEquals(10, checkCalendar.get(Calendar.MONTH));
        Assert.assertEquals(16, checkCalendar.get(Calendar.DAY_OF_MONTH));

        checkCalendar.setTime(sortedRegistrants.get(2).getCreatedDateRaw());
        Assert.assertEquals(2011, checkCalendar.get(Calendar.YEAR));
        Assert.assertEquals(9, checkCalendar.get(Calendar.MONTH));
        Assert.assertEquals(15, checkCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testExecute_SORT_FORM_NAME_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.FORM_NAME, false);
        Assert.assertEquals("form_name1", sortedRegistrants.get(0).getForms().get(0).getName());
        Assert.assertEquals("form_name2", sortedRegistrants.get(1).getForms().get(0).getName());
        Assert.assertEquals("form_name3", sortedRegistrants.get(2).getForms().get(0).getName());
    }

    @Test
    public void testExecute_SORT_STATUS_ASC() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, ManageRegistrantsSortType.STATUS, false);
        Assert.assertEquals("a", sortedRegistrants.get(0).getStatus());
        Assert.assertEquals("b", sortedRegistrants.get(1).getStatus());
        Assert.assertEquals("c", sortedRegistrants.get(2).getStatus());
    }

    @Test
    public void testExecuteWithoutSortType() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(registrants, null, false);
        Assert.assertNull(sortedRegistrants);
    }
    
    @Test
    public void testExecuteWithoutVisitors() {
        final List<RegisteredVisitorInfo> registrants = bulidRegisteredVisitorsList();

        final List<RegisteredVisitorInfo> sortedRegistrants =
                sorter.execute(null, ManageRegistrantsSortType.FIRST_NAME , false);
        Assert.assertNull(sortedRegistrants);
    }
    
    private List<RegisteredVisitorInfo> bulidRegisteredVisitorsList(){
        final List<RegisteredVisitorInfo> registeredVisitorsList = new ArrayList<RegisteredVisitorInfo>();

        final RegisteredVisitorInfo registeredVisitorInfo1 = new RegisteredVisitorInfo();
        registeredVisitorInfo1.setFirstName("Dmitry");
        registeredVisitorInfo1.setLastName("Solomadin");
        registeredVisitorInfo1.setEmail("dmitry.solomadin@gmail.com");
        final Calendar createdDateCalendar1 = new GregorianCalendar();
        createdDateCalendar1.set(Calendar.YEAR, 1988);
        createdDateCalendar1.set(Calendar.MONTH, 11);
        createdDateCalendar1.set(Calendar.DAY_OF_MONTH, 17);
        registeredVisitorInfo1.setCreatedDateRaw(createdDateCalendar1.getTime());
        final Calendar updatedDateCalendar1 = new GregorianCalendar();
        updatedDateCalendar1.set(Calendar.YEAR, 1988);
        updatedDateCalendar1.set(Calendar.MONTH, 11);
        updatedDateCalendar1.set(Calendar.DAY_OF_MONTH, 17);
        registeredVisitorInfo1.setUpdatedDateRaw(updatedDateCalendar1.getTime());
        final DraftForm regForm1 = TestUtil.createRegistrationForm();
        regForm1.setName("form_name1");
        registeredVisitorInfo1.setForms(new ArrayList<DraftForm>(){{
            add(regForm1);
        }});
        registeredVisitorInfo1.setStatus("a");
        registeredVisitorsList.add(registeredVisitorInfo1);

        final RegisteredVisitorInfo registeredVisitorInfo2 = new RegisteredVisitorInfo();
        registeredVisitorInfo2.setFirstName("Artem");
        registeredVisitorInfo2.setLastName("Stasuk");
        registeredVisitorInfo2.setEmail("artem.stasuk@gmail.com");
        final Calendar createdDateCalendar2 = new GregorianCalendar();
        createdDateCalendar2.set(Calendar.YEAR, 2010);
        createdDateCalendar2.set(Calendar.MONTH, 10);
        createdDateCalendar2.set(Calendar.DAY_OF_MONTH, 16);
        registeredVisitorInfo2.setCreatedDateRaw(createdDateCalendar2.getTime());
        final Calendar updatedDateCalendar2 = new GregorianCalendar();
        updatedDateCalendar2.set(Calendar.YEAR, 2010);
        updatedDateCalendar2.set(Calendar.MONTH, 10);
        updatedDateCalendar2.set(Calendar.DAY_OF_MONTH, 16);
        registeredVisitorInfo2.setUpdatedDateRaw(updatedDateCalendar2.getTime());
        final DraftForm regForm2 = TestUtil.createRegistrationForm();
        regForm2.setName("form_name3");
        registeredVisitorInfo2.setForms(new ArrayList<DraftForm>(){{
            add(regForm2);
        }});
        registeredVisitorInfo2.setStatus("c");
        registeredVisitorsList.add(registeredVisitorInfo2);

        final RegisteredVisitorInfo registeredVisitorInfo3 = new RegisteredVisitorInfo();
        registeredVisitorInfo3.setFirstName("Anatoly");
        registeredVisitorInfo3.setLastName("Balakirev");
        registeredVisitorInfo3.setEmail("anatoly.balakirev@gmail.com");
        final Calendar createdDateCalendar3 = new GregorianCalendar();
        createdDateCalendar3.set(Calendar.YEAR, 2011);
        createdDateCalendar3.set(Calendar.MONTH, 9);
        createdDateCalendar3.set(Calendar.DAY_OF_MONTH, 15);
        registeredVisitorInfo3.setCreatedDateRaw(createdDateCalendar3.getTime());
        final Calendar updatedDateCalendar3 = new GregorianCalendar();
        updatedDateCalendar3.set(Calendar.YEAR, 2011);
        updatedDateCalendar3.set(Calendar.MONTH, 9);
        updatedDateCalendar3.set(Calendar.DAY_OF_MONTH, 15);
        registeredVisitorInfo3.setUpdatedDateRaw(updatedDateCalendar3.getTime());
        final DraftForm regForm3 = TestUtil.createRegistrationForm();
        regForm3.setName("form_name2");
        registeredVisitorInfo3.setForms(new ArrayList<DraftForm>(){{
            add(regForm3);
        }});
        registeredVisitorInfo3.setStatus("b");
        registeredVisitorsList.add(registeredVisitorInfo3);

        return registeredVisitorsList;
    }
    
}
