/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.sample.hook;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Phone;
import com.liferay.portal.service.PhoneLocalServiceUtil;

/**
 * Listens for Phone model changes and removes the number only if its value
 * equals "0".
 *
 *@authorTomasz Wojewódka
 */
public class PhoneListener extends BaseModelListener<Phone> {
    public void onAfterCreate(Phone phone) throws ModelListenerException {
    	System.out.println(" #####   PhoneListener.onAfterCreate : phone"+ phone );
		 
    }

    public void onAfterRemove(Phone phone) throws ModelListenerException {
        regeneratePhone(phone, "onAfterRemove");
        System.out.println(" #####   PhoneListener.onAfterRemove : phone"+ phone );
    }

    public void onAfterUpdate(Phone phone) throws ModelListenerException {

    }

    public void onBeforeCreate(Phone phone) throws ModelListenerException {
    	 System.out.println(" #####   PhoneListener.onBeforeCreate : phone"+ phone );
    }

    public void onBeforeRemove(Phone phone) throws ModelListenerException {

    }

    public void onBeforeUpdate(Phone phone) throws ModelListenerException {

    }

    /**
     * Removes old phone only if number equals "0".
     *
     *@param phone
     *@param msg
     */
    private void regeneratePhone(Phone phone, String msg) {

        if (phone.getNumber().equals("0")) {
            _log.info("Removing phone number: " + phone.toString());
        } else {

            try {
                PhoneLocalServiceUtil.updatePhone(phone);
                _log.info("Handling bug LPS-17381: " + phone.toString());
            } catch (SystemException e) {
                _log.info("Exception during handling bug LPS-17381: "
                        + phone.toString());
                e.printStackTrace();
            }
        }

    }

    private static Log _log = LogFactoryUtil.getLog(PhoneListener.class);

}