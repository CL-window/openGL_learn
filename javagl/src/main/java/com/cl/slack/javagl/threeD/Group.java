package com.cl.slack.javagl.threeD;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

/**
 * 如果把Mesh比作Android的View ,Group可以看作Android的ViewGroup，
 * Android的View的设计也是采用的“Composite Pattern”。
 * Created by slack
 * on 17/3/31 下午9:12.
 */

public class Group extends Mesh {
    private Vector<Mesh> children = new Vector<Mesh>();

    @Override
    public void draw(GL10 gl) {
        int size = children.size();
        for( int i = 0; i < size; i++)
            children.get(i).draw(gl);
    }

    /**
     * @param location
     * @param object
     * @see java.util.Vector#add(int, java.lang.Object)
     */
    public void add(int location, Mesh object) {
        children.add(location, object);
    }

    /**
     * @param object
     * @return
     * @see java.util.Vector#add(java.lang.Object)
     */
    public boolean add(Mesh object) {
        return children.add(object);
    }

    /**
     *
     * @see java.util.Vector#clear()
     */
    public void clear() {
        children.clear();
    }

    /**
     * @param location
     * @return
     * @see java.util.Vector#get(int)
     */
    public Mesh get(int location) {
        return children.get(location);
    }

    /**
     * @param location
     * @return
     * @see java.util.Vector#remove(int)
     */
    public Mesh remove(int location) {
        return children.remove(location);
    }

    /**
     * @param object
     * @return
     * @see java.util.Vector#remove(java.lang.Object)
     */
    public boolean remove(Object object) {
        return children.remove(object);
    }

    /**
     * @return
     * @see java.util.Vector#size()
     */
    public int size() {
        return children.size();
    }

}
