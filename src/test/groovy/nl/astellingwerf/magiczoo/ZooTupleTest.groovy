package nl.astellingwerf.magiczoo

import spock.lang.Specification
import spock.lang.Unroll

class ZooTupleTest extends Specification {
    def 'given example'() {
        expect: verify 17, 55, 6, lions: 23
    }

    @Unroll
    def 'simple case (#goats goats, #wolves wolves, #lions lions)'(int goats, int wolves, int lions, Map<String, Integer> result) {
        expect: verify goats, wolves, lions, result

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
        verify goats, wolves, lions, result

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

    private boolean verify(int goats, int wolves, int lions, Map<String, Integer> result) {
        new ZooTuple(result) == new ZooTuple(goats: goats, wolves: wolves, lions: lions).solve()
    }

    private boolean verify(Map<String, Integer> result, int goats, int wolves, int lions) {
        verify goats, wolves, lions, result
    }
}
