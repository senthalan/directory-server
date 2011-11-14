/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.directory.server.core.shared.txn.logedit;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.UUID;

import org.apache.directory.server.core.api.partition.index.Index;
import org.apache.directory.server.core.api.partition.index.Serializer;
import org.apache.directory.server.core.api.txn.logedit.AbstractDataChange;
import org.apache.directory.server.core.shared.txn.TxnManagerFactory;

import org.apache.directory.shared.ldap.model.entry.Value;


/**
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class IndexChange extends AbstractDataChange
{
    /** Index this change is done on */
    private transient Index<?> index;

    /** oid of the attribute the index is on */
    private String oid;

    /** key of the forward index */
    private Object key;

    /** id for the index */
    private UUID id;

    /** Change type */
    private Type type;

    /** Whether the index is a system index. False is user index */
    private boolean isSystemIndex;

    // For externalizable
    public IndexChange()
    {
    }


    public IndexChange( Index<?> index, String oid, Object key, UUID id, Type type, boolean isSystemIndex )
    {
        this.index = index;
        this.oid = oid;
        this.key = key;
        this.id = id;
        this.type = type;
        this.isSystemIndex = isSystemIndex;
    }


    public String getOID()
    {
        return oid;
    }


    public Index<?> getIndex()
    {
        return index;
    }


    public Object getKey()
    {
        return key;
    }


    public UUID getID()
    {
        return id;
    }


    public Type getType()
    {
        return type;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void readExternal( ObjectInput in ) throws IOException, ClassNotFoundException
    {
        oid = in.readUTF();
        key = in.readObject();
        id = UUID.fromString( in.readUTF() );
        type = Type.values()[in.readInt()];
    }


    @Override
    public void writeExternal( ObjectOutput out ) throws IOException
    {
        out.writeUTF( oid );
        out.writeObject( key );
        out.writeUTF( id.toString() );
        out.writeInt( type.ordinal() );
    }

    public enum Type
    {
        ADD,
        DELETE
    }
}
