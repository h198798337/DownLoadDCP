package com.my.test;
import java.nio.ByteBuffer;

public class ByteBufferTest {

        /**
         * @param args
         */
        public static void main(String[] args) {
                // TODO Auto-generated method stub
                ByteBuffer bb = ByteBuffer.allocate(10);
                v(bb.capacity());//初始的capacity为10
                v(bb.limit());//初始的limit为10
                
                //printBuffer(bb);
                v(bb.position());
                bb.putChar('a');
                v(bb.position()); //因为char类型占两个字节，所以指针指向2
                
                //bb.limit(2);//如果limit设置为2，调用下句会出现异常
                bb.put("asd".getBytes());//放入三个字节数据
                
                v(bb.position());//position变成5
                
                bb.flip();
                
                v(bb.position());//position变成0
                v(bb.limit());//limit变成5
                
                bb.position(1);//把当前位置变成1
                bb.compact();//把后面的字节先前移动一个位置
                
                v(bb.position());//当前的position变为4
                v(bb.limit());//limit重置为capacity
                
                printBuffer(bb);
                
                bb.clear();
                v(bb.position());//当前的position变为0
                v(bb.limit());//limit重置为capacity
                                
                System.exit(0);
        }
        
    public static void v(Object o){
        System.out.println(o);
    }
    
    public static void printBuffer(ByteBuffer buffer){
            int p = buffer.position();
            buffer.position(0);
            for(int i=0;i<buffer.limit();i++){
                    v(Integer.toHexString(buffer.get()));
            }
            buffer.position(p);
    }

}
