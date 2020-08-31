
public class MainClass {

    public static void main(String[] args) {
        //Tester test1 = new Tester(null, null, null, new StudentClass());
        //Tester test2 = new Tester(int.class, null, null, new StudentClass());
        Class[] classes = {String.class, int.class};
        Object[] inputs = {"ariel",2};
        Tester test3 = new Tester(String.class, classes, inputs, null);
        System.out.println("grade= " + test3.getGrade());
    }
}
