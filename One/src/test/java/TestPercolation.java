/**
 * Created by jianghong on 2016/11/12.
 */

import org.junit.Test;
import org.junit.Assert;

public class TestPercolation {

    @Test
    public void testXyTo1D() {
        Percolation perc = new Percolation(2);
        Assert.assertEquals(false, perc.percolates());
    }
}
