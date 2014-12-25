package nl.astellingwerf.magiczoo

import spock.lang.Specification
import spock.lang.Unroll

class ZooTupleTest extends Specification {
        setup:
        ZooTuple t = new ZooTuple(17, 55, 6)
        when:
        ZooTuple result = t.solve()
        then:
        result == new ZooTuple(lions: 23);
    def 'given example'() {
    }

    @Unroll
        expect:
        new ZooTuple(result) == new ZooTuple(goats: goats, wolves: wolves, lions: lions).solve()
    def 'simple case (#goats goats, #wolves wolves, #lions lions)'(int goats, int wolves, int lions, Map<String, Integer> result) {

        where:
        goats | wolves | lions || result
        0     | 0      | 0     || [:]
        1     | 0      | 0     || [goats: 1]
        1     | 1      | 0     || [lions: 1]
        2     | 1      | 1     || [goats: 3]
        1     | 2      | 1     || [wolves: 3]
        1     | 1      | 2     || [lions: 3]
        2     | 1      | 2     || [wolves: 3]
        3     | 1      | 2     || [lions: 3]
        5     | 1      | 2     || [lions: 3]
        7     | 1      | 2     || [lions: 3]
    }

    @Unroll
    def 'ambiguous case (#goats goats, #wolves wolves, #lions lions)'(int goats, int wolves, int lions, Map<String, Integer> result) {
        /* In some cases, multiple answers are possible, but since the assignment tells to return a single tuple, it's
         * been implemented accordingly. The implementation has a bias:
         * The last biggest number.
         */
        expect:
        new ZooTuple(result) == new ZooTuple(goats: goats, wolves: wolves, lions: lions).solve()

        where:
        goats | wolves | lions || result
        1     | 1      | 1     || [lions: 2]
        2     | 2      | 2     || [lions: 4]

        1     | 3      | 3     || [lions: 4]
        2     | 0      | 2     || [lions: 2]
        12    | 8      | 12    || [lions: 20]
        2     | 2      | 0     || [wolves: 2]
        12    | 12     | 8     || [wolves: 20]
    }
}
