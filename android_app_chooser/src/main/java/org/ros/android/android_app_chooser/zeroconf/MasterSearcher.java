/*
 * Software License Agreement (BSD License)
 *
 * Copyright (c) 2011, Willow Garage, Inc.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above
 *    copyright notice, this list of conditions and the following
 *    disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 *  * Neither the name of Willow Garage, Inc. nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.ros.android.android_app_chooser.zeroconf;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.widget.ListView;

import com.github.rosjava.jmdns.DiscoveredService;
import com.github.rosjava.jmdns.Zeroconf;



public class MasterSearcher {
	
	private Zeroconf zeroconf;
	private ArrayList<DiscoveredService> discoveredMasters;
	private DiscoveryAdapter discoveryAdapter;
	private DiscoveryHandler discoveryHandler;
	private Logger logger;

	public MasterSearcher(Context context,final ListView listView) {

		discoveredMasters = new ArrayList<DiscoveredService>();
		
		discoveryAdapter = new DiscoveryAdapter(context,discoveredMasters);
		listView.setAdapter(discoveryAdapter);
		listView.setItemsCanFocus(false);
	    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		logger = new Logger();
		zeroconf = new Zeroconf(logger);
		discoveryHandler = new DiscoveryHandler(discoveryAdapter,discoveredMasters);
		zeroconf.setDefaultDiscoveryCallback(discoveryHandler);
		
		new DiscoverySetup(context).execute(zeroconf);
	}

	public void shutdown() {
	    try {
	    	zeroconf.shutdown();
        } catch (IOException e) {
	        e.printStackTrace();
        }
    }
	
}