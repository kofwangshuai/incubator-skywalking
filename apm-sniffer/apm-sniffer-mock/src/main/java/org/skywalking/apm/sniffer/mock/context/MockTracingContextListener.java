package org.skywalking.apm.sniffer.mock.context;

import org.junit.Assert;
import org.skywalking.apm.agent.core.context.TracingContextListener;
import org.skywalking.apm.agent.core.context.trace.TraceSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is mock tracer context listener, which should be added by calling {@link TracerContext.ListenerManager#add(TracingContextListener)}.
 * This mock listener will hold all finished trace, which all are generated by {@link TracerContext#finish()}.
 * <p>
 * Created by wusheng on 2017/2/20.
 */
public class MockTracingContextListener implements TracingContextListener {
    private List<TraceSegment> finishedTraceSegments = Collections.synchronizedList(new ArrayList<TraceSegment>());

    @Override
    public void afterFinished(TraceSegment traceSegment) {
        finishedTraceSegments.add(traceSegment);
    }

    /**
     * Assert all finished {@link #finishedTraceSegments} match the given size.
     *
     * @param size the give size.
     */
    public void assertSize(int size) {
        Assert.assertEquals(size, finishedTraceSegments.size());
    }

    /**
     * Assert the given index is a valid index of {@link #finishedTraceSegments}
     *
     * @param index the given index.
     */
    public void assertValidIndex(int index) {
        Assert.assertTrue(index < finishedTraceSegments.size());
    }

    /**
     * Assert the {@link TraceSegment} at the given index of {@link #finishedTraceSegments},
     * and run the given {@link SegmentAssert#call(TraceSegment)} to assert.
     *
     * @param index         the given index.
     * @param segmentAssert the given assert.
     */
    public void assertTraceSegment(int index, SegmentAssert segmentAssert) {
        assertValidIndex(index);
        segmentAssert.call(finishedTraceSegments.get(index));
    }

    /**
     * Clear all hold data.
     */
    public void clear() {
        finishedTraceSegments.clear();
    }

    /**
     * Get {@link TraceSegment} of the given index.
     *
     * @param index
     * @return
     */
    public TraceSegment getFinished(int index) {
        assertSize(index + 1);
        return finishedTraceSegments.get(index);
    }
}
