/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.pool2.impl;

import static org.junit.Assert.assertEquals;

import org.apache.commons.pool2.KeyedObjectPoolFactory;
import org.apache.commons.pool2.KeyedPoolableObjectFactory;
import org.apache.commons.pool2.TestKeyedObjectPoolFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool2.impl.WhenExhaustedAction;
import org.junit.Test;

/**
 * Tests for {@link GenericKeyedObjectPoolFactory}.
 *
 * @author Sandy McArthur
 * @version $Revision$ $Date$
 */
public class TestGenericKeyedObjectPoolFactory extends TestKeyedObjectPoolFactory {
    @Override
    protected KeyedObjectPoolFactory<Object,Object> makeFactory(final KeyedPoolableObjectFactory<Object,Object> objectFactory) {
        return new GenericKeyedObjectPoolFactory<Object,Object>(objectFactory);
    }

    @Test
    public void testConstructors() throws Exception {
        GenericKeyedObjectPoolFactory<Object,Object> factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory());
        factory.createPool().close();
        GenericKeyedObjectPool<Object,Object> pool;


        final GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
        config.setMaxActive(1);
        config.setMaxIdle(2);
        config.setMaxWait(3);
        config.setMinIdle(4);
        config.setMinEvictableIdleTimeMillis(5);
        config.setNumTestsPerEvictionRun(6);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(false);
        config.setTestWhileIdle(true);
        config.setTimeBetweenEvictionRunsMillis(8);
        config.setWhenExhaustedAction(WhenExhaustedAction.GROW);
        config.setLifo(false);
        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), config);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxIdle());
        assertEquals(3, pool.getMaxWait());
        assertEquals(4, pool.getMinIdle());
        assertEquals(5, pool.getMinEvictableIdleTimeMillis());
        assertEquals(6, pool.getNumTestsPerEvictionRun());
        assertEquals(true, pool.getTestOnBorrow());
        assertEquals(false, pool.getTestOnReturn());
        assertEquals(true, pool.getTestWhileIdle());
        assertEquals(false, pool.getLifo());
        assertEquals(8, pool.getTimeBetweenEvictionRunsMillis());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.BLOCK, 125);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(WhenExhaustedAction.BLOCK, pool.getWhenExhaustedAction());
        assertEquals(125, pool.getMaxWait());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.GROW, 2, true, false);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxWait());
        assertEquals(true, pool.getTestOnBorrow());
        assertEquals(false, pool.getTestOnReturn());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.GROW, 2, 3);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxWait());
        assertEquals(3, pool.getMaxIdle());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.GROW, 2, 3, 4);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxWait());
        assertEquals(3, pool.getMaxIdle());
        assertEquals(4, pool.getMaxTotal());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.GROW, 2, 3, true, false);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxWait());
        assertEquals(3, pool.getMaxIdle());
        assertEquals(true, pool.getTestOnBorrow());
        assertEquals(false, pool.getTestOnReturn());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.GROW, 2, 3, true, false, 4, 5, 6, false);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxWait());
        assertEquals(3, pool.getMaxIdle());
        assertEquals(4, pool.getTimeBetweenEvictionRunsMillis());
        assertEquals(5, pool.getNumTestsPerEvictionRun());
        assertEquals(6, pool.getMinEvictableIdleTimeMillis());
        assertEquals(true, pool.getTestOnBorrow());
        assertEquals(false, pool.getTestOnReturn());
        assertEquals(false, pool.getTestWhileIdle());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();


        factory = new GenericKeyedObjectPoolFactory<Object,Object>(createObjectFactory(), 1, WhenExhaustedAction.GROW, 2, 3, 4, true, false, 5, 6, 7, true);
        pool = (GenericKeyedObjectPool<Object,Object>)factory.createPool();
        assertEquals(1, pool.getMaxActive());
        assertEquals(2, pool.getMaxWait());
        assertEquals(3, pool.getMaxIdle());
        assertEquals(4, pool.getMaxTotal());
        assertEquals(5, pool.getTimeBetweenEvictionRunsMillis());
        assertEquals(6, pool.getNumTestsPerEvictionRun());
        assertEquals(7, pool.getMinEvictableIdleTimeMillis());
        assertEquals(true, pool.getTestOnBorrow());
        assertEquals(false, pool.getTestOnReturn());
        assertEquals(true, pool.getTestWhileIdle());
        assertEquals(WhenExhaustedAction.GROW, pool.getWhenExhaustedAction());
        pool.close();
    }
}