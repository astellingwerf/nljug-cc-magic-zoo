package nl.astellingwerf.magiczoo

import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString(includeNames = true, includePackage = false)
@TupleConstructor
class ZooTuple {
    int goats;
    int wolves;
    int lions;

    public ZooTuple(int goats = 0, int wolves = 0, int lions = 0) {
        this.goats = goats
        this.wolves = wolves
        this.lions = lions
    }

    public ZooTuple solve() {

        Closure<Boolean> isEven = { it -> 0 == (it(this) % 2) }
        Closure<Integer> goats = this.&getGoats
        Closure<Integer> wolves = this.&getWolves
        Closure<Integer> lions = this.&getLions

        def xyzTriplet = [goats, wolves, lions]   // Over all kinds...
                .split(isEven)                    // ...separate the even from the odd.
                .collect { it.sort { it() } }     // Within the groups of even and odd numbers, sort from small to large.
                .sort { -it.size() }              // Put the groups of even and odd numbers in order by size, large first...
                .flatten()                        // ...and then join the kinds back into a single list.
        Closure<Integer> z = xyzTriplet.first()
        Closure<Integer> x = xyzTriplet.last()

        int maxPopulation = x() + z()
        String kindWithMaxPopulation = x != goats ? (x == wolves ? 'wolves' : 'lions') : 'goats'
        new ZooTuple((kindWithMaxPopulation): maxPopulation)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        ZooTuple tuple = (ZooTuple) o
        return goats == tuple.goats && lions == tuple.lions && wolves == tuple.wolves
    }

    int hashCode() {
        int result
        result = goats
        result = 31 * result + wolves
        result = 31 * result + lions
        return result
    }

    public static void main(String[] args) {
        println new ZooTuple(args[0] as int, args[1] as int, args[2] as int).solve()
    }
}
