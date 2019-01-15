package snownee.kiwi.util;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class TagUtil
{
    public static class Tag
    {
        public static final int END = 0;
        public static final int BYTE = 1;
        public static final int SHORT = 2;
        public static final int INT = 3;
        public static final int LONG = 4;
        public static final int FLOAT = 5;
        public static final int DOUBLE = 6;
        public static final int BYTE_ARRAY = 7;
        public static final int STRING = 8;
        public static final int LIST = 9;
        public static final int COMPOUND = 10;
        public static final int INT_ARRAY = 11;
        public static final int LONG_ARRAY = 12;
        public static final int ANY_NUMERIC = 99;
    }

    @Nullable
    private ItemStack stack;
    @Nullable
    private NBTTagCompound tag;

    private TagUtil(@Nullable NBTTagCompound tag, @Nullable ItemStack stack)
    {
        this.stack = stack;
        this.tag = tag;
    }

    public NBTTagCompound getTag(String key)
    {
        return getTag(key, true);
    }

    public NBTTagCompound getTag(String key, boolean createIfNull)
    {
        return getTagInternal(key, createIfNull, false);
    }

    private NBTTagCompound getTagInternal(String key, boolean createIfNull, boolean ignoreLastNode)
    {
        if (tag == null)
        {
            if (createIfNull)
            {
                tag = new NBTTagCompound();
                if (stack != null)
                {
                    stack.setTagCompound(tag);
                }
            }
            else
            {
                return null;
            }
        }
        if (key.isEmpty())
        {
            return tag;
        }
        NBTTagCompound subTag = tag;
        String[] parts = key.split("\\.");
        int length = parts.length;
        if (ignoreLastNode)
        {
            --length;
        }
        for (int i = 0; i < length; ++i)
        {
            if (!subTag.hasKey(parts[i], Tag.COMPOUND))
            {
                if (createIfNull)
                {
                    subTag.setTag(parts[i], new NBTTagCompound());
                }
                else
                {
                    return null;
                }
            }
            subTag = subTag.getCompoundTag(parts[i]);
        }
        return subTag;
    }

    private NBTTagCompound getTagInternal(String key)
    {
        return getTagInternal(key, true, true);
    }

    private String getLastNode(String key)
    {
        int index = key.lastIndexOf(".");
        if (index < 0)
        {
            return key;
        }
        else
        {
            return key.substring(index + 1);
        }
    }

    public TagUtil setTag(String key, NBTBase value)
    {
        getTagInternal(key).setTag(getLastNode(key), value);
        return this;
    }

    public TagUtil setInt(String key, int value)
    {
        getTagInternal(key).setInteger(getLastNode(key), value);
        return this;
    }

    public int getInt(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.INT) ? subTag.getInteger(actualKey) : 0;
    }

    public TagUtil setLong(String key, long value)
    {
        getTagInternal(key).setLong(getLastNode(key), value);
        return this;
    }

    public long getLong(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.LONG) ? subTag.getLong(actualKey) : 0;
    }

    public TagUtil setShort(String key, short value)
    {
        getTagInternal(key).setShort(getLastNode(key), value);
        return this;
    }

    public long getShort(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.SHORT) ? subTag.getShort(actualKey) : 0;
    }

    public TagUtil setDouble(String key, double value)
    {
        getTagInternal(key).setDouble(getLastNode(key), value);
        return this;
    }

    public double getDouble(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.DOUBLE) ? subTag.getDouble(actualKey) : 0;
    }

    public TagUtil setFloat(String key, float value)
    {
        getTagInternal(key).setFloat(getLastNode(key), value);
        return this;
    }

    public float getFloat(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.FLOAT) ? subTag.getFloat(actualKey) : 0;
    }

    public TagUtil setByte(String key, byte value)
    {
        getTagInternal(key).setFloat(getLastNode(key), value);
        return this;
    }

    public byte getByte(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.BYTE) ? subTag.getByte(actualKey) : 0;
    }

    public TagUtil setString(String key, String value)
    {
        getTagInternal(key).setString(getLastNode(key), value);
        return this;
    }

    public String getString(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.STRING) ? subTag.getString(actualKey) : "";
    }

    public TagUtil setIntArray(String key, int[] value)
    {
        getTagInternal(key).setIntArray(getLastNode(key), value);
        return this;
    }

    public int[] getIntArray(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.INT_ARRAY) ? subTag.getIntArray(actualKey) : new int[0];
    }

    public TagUtil setByteArray(String key, byte[] value)
    {
        getTagInternal(key).setByteArray(getLastNode(key), value);
        return this;
    }

    public byte[] getByteArray(String key)
    {
        NBTTagCompound subTag = getTagInternal(key);
        String actualKey = getLastNode(key);
        return subTag.hasKey(actualKey, Tag.BYTE_ARRAY) ? subTag.getByteArray(actualKey) : new byte[0];
    }

    public boolean hasTag(String key, int type)
    {
        NBTTagCompound subTag = getTagInternal(key, false, true);
        if (subTag != null)
        {
            String actualKey = getLastNode(key);
            return subTag.hasKey(actualKey, type);
        }
        return false;
    }

    // TODO: remove parent if empty?
    public TagUtil remove(String key)
    {
        NBTTagCompound subTag = getTagInternal(key, false, true);
        if (subTag != null)
        {
            String actualKey = getLastNode(key);
            subTag.removeTag(actualKey);
        }
        return this;
    }

    public NBTTagCompound get()
    {
        return tag;
    }

    public static TagUtil of(ItemStack stack)
    {
        return new TagUtil(stack.getTagCompound(), stack);
    }

    public static TagUtil of(NBTTagCompound tag)
    {
        return new TagUtil(tag, null);
    }

    public static TagUtil of()
    {
        return new TagUtil(null, null);
    }

}
