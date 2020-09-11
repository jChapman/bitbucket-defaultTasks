package ut.com.superfluoussextant.defaulttasks;

import org.junit.Test;
import com.superfluoussextant.defaulttasks.api.MyPluginComponent;
import com.superfluoussextant.defaulttasks.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}