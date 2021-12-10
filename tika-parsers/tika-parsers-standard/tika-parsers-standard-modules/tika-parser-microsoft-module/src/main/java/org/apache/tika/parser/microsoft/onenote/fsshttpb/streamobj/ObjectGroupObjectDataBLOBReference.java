/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tika.parser.microsoft.onenote.fsshttpb.streamobj;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.microsoft.onenote.fsshttpb.streamobj.basic.BasicObject;
import org.apache.tika.parser.microsoft.onenote.fsshttpb.streamobj.basic.CellIDArray;
import org.apache.tika.parser.microsoft.onenote.fsshttpb.streamobj.basic.ExGUIDArray;
import org.apache.tika.parser.microsoft.onenote.fsshttpb.streamobj.basic.ExGuid;

/**
 * object data BLOB reference
 */
public class ObjectGroupObjectDataBLOBReference extends StreamObject {
    public ExGUIDArray objectExtendedGUIDArray;
    public CellIDArray cellIDArray;
    public ExGuid blobExtendedGUID;

    /**
     * Initializes a new instance of the ObjectGroupObjectDataBLOBReference class.
     */
    public ObjectGroupObjectDataBLOBReference() {
        super(StreamObjectTypeHeaderStart.ObjectGroupObjectDataBLOBReference);
        this.objectExtendedGUIDArray = new ExGUIDArray();
        this.cellIDArray = new CellIDArray();
        this.blobExtendedGUID = new ExGuid();
    }

    /**
     * Used to de-serialize the element.
     *
     * @param byteArray     A Byte array
     * @param currentIndex  Start position
     * @param lengthOfItems The length of the items
     */
    @Override
    protected void deserializeItemsFromByteArray(byte[] byteArray, AtomicInteger currentIndex,
                                                 int lengthOfItems)
            throws TikaException, IOException {
        AtomicInteger index = new AtomicInteger(currentIndex.get());
        this.objectExtendedGUIDArray = BasicObject.parse(byteArray, index, ExGUIDArray.class);
        this.cellIDArray = BasicObject.parse(byteArray, index, CellIDArray.class);
        this.blobExtendedGUID = BasicObject.parse(byteArray, index, ExGuid.class);

        if (index.get() - currentIndex.get() != lengthOfItems) {
            throw new StreamObjectParseErrorException(currentIndex.get(),
                    "ObjectGroupObjectDataBLOBReference", "Stream object over-parse error", null);
        }

        currentIndex.set(index.get());
    }

    /**
     * Used to convert the element into a byte List.
     *
     * @param byteList A Byte list
     * @return The number of the elements
     */
    @Override
    protected int serializeItemsToByteList(List<Byte> byteList) throws IOException {
        int itemsIndex = byteList.size();
        byteList.addAll(this.objectExtendedGUIDArray.serializeToByteList());
        byteList.addAll(cellIDArray.serializeToByteList());
        byteList.addAll(this.blobExtendedGUID.serializeToByteList());
        return byteList.size() - itemsIndex;
    }
}
