import org.kohsuke.stapler.StaplerRequest
import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import net.sf.json.JSONObject
import net.sf.json.JSONArray
import jenkins.model.Jenkins.CloudList

JSONObject docker_settings = new JSONObject()
docker_settings.putAll([
    name: 'docker-local',
    serverUrl: 'http://127.0.0.1:4243',
    containerCapStr: '50',
    connectionTimeout: 5,
    readTimeout: 15,
    credentialsId: '',
    version: '',
    templates: [
        [
            image: 'jervis-docker-jvm:latest',
            labelString: 'jervis-docker',
            remoteFs: '',
            credentialsId: '',
            idleTerminationMinutes: '5',
            sshLaunchTimeoutMinutes: '1',
            jvmOptions: '',
            javaPath: '',
            memoryLimit: 0,
            cpuShares: 0,
            prefixStartSlaveCmd: '',
            suffixStartSlaveCmd: '',
            instanceCapStr: '50',
            dnsString: '',
            dockerCommand: '/sbin/my_init',
            volumesString: '',
            volumesFromString: '',
            hostname: '',
            bindPorts: '',
            bindAllPorts: false,
            privileged: false,
            tty: false,
            macAddress: ''
        ]
    ]
])

def bindJSONToList( Class type, Object src) {
    if(type == DockerTemplate){
        ArrayList r = new ArrayList();
        if (src instanceof JSONObject) {
            JSONObject temp = (JSONObject) src;
            r.add(
                    new DockerTemplate(temp.optString("image"),
                                       temp.optString("labelString"),
                                       temp.optString("remoteFs"),
                                       temp.optString("remoteFsMapping"),
                                       temp.optString("credentialsId"),
                                       temp.optString("idleTerminationMinutes"),
                                       temp.optString("sshLaunchTimeoutMinutes"),
                                       temp.optString("jvmOptions"),
                                       temp.optString("javaPath"),
                                       temp.optInt("memoryLimit"),
                                       temp.optInt("cpuShares"),
                                       temp.optString("prefixStartSlaveCmd"),
                                       temp.optString("suffixStartSlaveCmd"),
                                       temp.optString("instanceCapStr"),
                                       temp.optString("dnsString"),
                                       temp.optString("dockerCommand"),
                                       temp.optString("volumesString"),
                                       temp.optString("volumesFromString"),
                                       temp.optString("environmentsString"),
                                       temp.optString("lxcConfString"),
                                       temp.optString("hostname"),
                                       temp.optString("bindPorts"),
                                       temp.optBoolean("bindAllPorts"),
                                       temp.optBoolean("privileged"),
                                       temp.optBoolean("tty"),
                                       temp.optString("macAddress")
                )
            );
        }
        if (src instanceof JSONArray) {
            JSONArray creds_array = (JSONArray) src;
            for (Object o : creds_array) {
                if (o instanceof JSONObject) {
                    JSONObject temp = (JSONObject) o;
                    r.add(
                            new DockerTemplate(temp.optString("image"),
                                               temp.optString("labelString"),
                                               temp.optString("remoteFs"),
                                               temp.optString("remoteFsMapping"),
                                               temp.optString("credentialsId"),
                                               temp.optString("idleTerminationMinutes"),
                                               temp.optString("sshLaunchTimeoutMinutes"),
                                               temp.optString("jvmOptions"),
                                               temp.optString("javaPath"),
                                               temp.optInt("memoryLimit"),
                                               temp.optInt("cpuShares"),
                                               temp.optString("prefixStartSlaveCmd"),
                                               temp.optString("suffixStartSlaveCmd"),
                                               temp.optString("instanceCapStr"),
                                               temp.optString("dnsString"),
                                               temp.optString("dockerCommand"),
                                               temp.optString("volumesString"),
                                               temp.optString("volumesFromString"),
                                               temp.optString("environmentsString"),
                                               temp.optString("lxcConfString"),
                                               temp.optString("hostname"),
                                               temp.optString("bindPorts"),
                                               temp.optBoolean("bindAllPorts"),
                                               temp.optBoolean("privileged"),
                                               temp.optBoolean("tty"),
                                               temp.optString("macAddress")
                        )
                    );
                }
            }
        }
        return r;
    }
    if(type == DockerCloud){
        CloudList r = new CloudList();
        if (src instanceof JSONObject) {
            JSONObject temp = (JSONObject) src;
            r.add(
                new DockerCloud(temp.optString("name"),
                                bindJSONToList(DockerTemplate.class, temp.optJSONArray("templates")),
                                temp.optString("serverUrl"),
                                temp.optString("containerCapStr"),
                                temp.optInt("connectTimeout", 5),
                                temp.optInt("readTimeout", 15),
                                temp.optString("credentialsId"),
                                temp.optString("version")
                )
            );
        }
        if (src instanceof JSONArray) {
            JSONArray creds_array = (JSONArray) src;
            for (Object o : creds_array) {
                if (o instanceof JSONObject) {
                    JSONObject temp = (JSONObject) src;
                    r.add(
                        new DockerCloud(temp.optString("name"),
                                        bindJSONToList(DockerTemplate.class, temp.optJSONArray("templates")),
                                        temp.optString("serverUrl"),
                                        temp.optString("containerCapStr"),
                                        temp.optInt("connectTimeout", 5),
                                        temp.optInt("readTimeout", 15),
                                        temp.optString("credentialsId"),
                                        temp.optString("version")
                        )
                    );
                }
            }
        }
        return r;
    }
}

def req = [
    bindJSONToList: { Class type, Object src ->
        bindJSONToList(type, src)
    }
] as org.kohsuke.stapler.StaplerRequest

Jenkins.instance.clouds.add(req.bindJSONToList(DockerCloud.class, docker_settings))
