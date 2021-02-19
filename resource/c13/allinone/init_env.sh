# 初始化
init_security() {
systemctl stop firewalld
systemctl disable firewalld &>/dev/null
setenforce 0
sed -i '/^SELINUX=/ s/enforcing/disabled/'  /etc/selinux/config
sed -i '/^GSSAPIAu/ s/yes/no/' /etc/ssh/sshd_config
sed -i '/^#UseDNS/ {s/^#//;s/yes/no/}' /etc/ssh/sshd_config
systemctl enable sshd crond &> /dev/null
rpm -e postfix --nodeps
echo -e "\033[32m [安全配置] ==> OK \033[0m"
}
init_security

init_yumsource() {
if [ ! -d /etc/yum.repos.d/backup ];then
    mkdir /etc/yum.repos.d/backup
fi
mv /etc/yum.repos.d/* /etc/yum.repos.d/backup 2>/dev/null
if ! ping -c2 www.baidu.com &>/dev/null    
then
    echo "您无法上外网，不能配置yum源"
    exit    
fi
    curl -o /etc/yum.repos.d/163.repo http://mirrors.163.com/.help/CentOS7-Base-163.repo &>/dev/null
    curl -o /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo &>/dev/null
    yum clean all
    timedatectl set-timezone Asia/Shanghai
    echo "nameserver 114.114.114.114" > /etc/resolv.conf
    echo "nameserver 8.8.8.8" >> /etc/resolv.conf
    chattr +i /etc/resolv.conf
    yum -y install ntpdate
    ntpdate -b  ntp1.aliyun.com        # 对时很重要
    echo -e "\033[32m [YUM　Source] ==> OK \033[0m"
}
init_yumsource


