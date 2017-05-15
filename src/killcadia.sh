for pid in $(ps -ef | grep "./cadiaplayer" | awk '{print $2}'); do kill $pid; done
