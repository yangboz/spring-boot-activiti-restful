/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * All rights reserved.
 */
package info.smartkit.eip.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @see: http://stackoverflow.com/questions/2401489/execute-method-on-startup-in-spring
 * @see: http://blog.comsysto.com/2014/10/17/spring-boot-my-favorite-timesaving-conventionenabling-autoconfigcreating-
 *       beanmaking-classpathshaking-microcontainer/
 * @author yangboz
 */
@Component
public class StartupHouseKeeper implements ApplicationListener<ContextRefreshedEvent>
{
    private static Logger LOG = LogManager.getLogger(StartupHouseKeeper.class);

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        LOG.debug("onApplicationEvent!");
        //
    }

}
