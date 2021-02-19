ip=$(cat /etc/hosts | tail -n 1 | awk '{print $1}')
echo $ip

/opt/spark/bin/spark-submit --master k8s://https://172.16.2.62:6443 \
            --deploy-mode client     \
            --class streaming.core.StreamingApp  \
            --conf spark.kubernetes.container.image=172.16.2.66:5000/spark:v3.0 \
            --conf spark.kubernetes.container.image.pullPolicy=Always  \
            --conf spark.kubernetes.namespace=default                            \
            --conf spark.kubernetes.executor.request.cores=1  \
            --conf spark.kubernetes.executor.limit.cores=1                   \
            --conf spark.kubernetes.driver.volumes.hostPath.mlsql.mount.path=/tmp/xxx \
            --conf spark.kubernetes.driver.volumes.hostPath.mlsql.options.claimName=spark-pvc-claim \
            --conf spark.dynamicAllocation.enabled=true  \
            --conf spark.dynamicAllocation.shuffleTracking.enabled=true \
            --conf spark.dynamicAllocation.minExecutors=1 \
            --conf spark.dynamicAllocation.maxExecutors=4 \
            --conf spark.dynamicAllocation.executorIdleTimeout=60 \
            --conf spark.jars.ivy=/tmp/.ivy \
            --conf spark.driver.host=$ip \
            --conf spark.sql.cbo.enabled=true  \
            --conf spark.sql.adaptive.enabled=true  \
            --conf spark.sql.cbo.joinReorder.enabled=true  \
            --conf spark.sql.cbo.planStats.enabled=true  \
            --conf spark.sql.cbo.starSchemaDetection=true  \
            --conf spark.driver.maxResultSize=2g  \
            --conf spark.serializer=org.apache.spark.serializer.KryoSerializer  \
            --conf spark.kryoserializer.buffer.max=200m \
            --conf spark.executor.extraJavaOptions="-XX:+UnlockExperimentalVMOptions -XX:+UseZGC -XX:+UseContainerSupport  -Dio.netty.tryReflectionSetAccessible=true" \
            --conf spark.driver.extraJavaOptions="-XX:+UnlockExperimentalVMOptions -XX:+UseZGC -XX:+UseContainerSupport  -Dio.netty.tryReflectionSetAccessible=true -DREALTIME_LOG_HOME=/tmp/__mlsql__/logs" \
            /opt/mlsql/streamingpro-mlsql-spark_3.0_2.12-2.1.0-SNAPSHOT.jar \
            -streaming.name mlsql \
            -streaming.rest true  \
            -streaming.thrift false  \
            -streaming.platform spark  \
            -streaming.enableHiveSupport true  \
            -streaming.spark.service true  \
            -streaming.job.cancel true              \
            -streaming.driver.port 9003
