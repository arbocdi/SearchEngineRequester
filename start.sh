#!/bin/bash  
#cd /usr/local/java/project_name
class="net.sf.arbocdi.ser.Launcher"
classpath="-cp target/*:target/dependency/compile/*:target/dependency/runtime/"
jmx_port="10100"
jmx="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$jmx_port -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=true -Dcom.sun.management.jmxremote.password.file=jmx.password  -Dcom.sun.management.jmxremote.access.file=jmx.access -Dcom.sun.management.jmxremote.ssl=false"
opts="-Djava.net.preferIPv4Stack=true"
cmd="java $opts $classpath $class"
$cmd
