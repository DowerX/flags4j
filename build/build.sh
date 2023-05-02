#!/bin/sh

# Usage:    ./build.sh [clean|build|run] [args for building] [args for running]
# Example:  ./build.sh clean,build,run "" -nogui

SRC_DIR="../src"
LIB_DIR="../lib"

JAVAC="javac"
JAVA="java"

RED="\033[0;31m"
GRN="\033[0;32m"
CYN="\033[0;36m"
CLR="\033[0m"

status=0

clean() {
    echo -n "Deleting class files... "
    find -name "*.class" > /tmp/class.txt
    count=$(cat /tmp/class.txt | wc -l )
    if [[ count -gt 1 ]]; then
        rm $(cat /tmp/class.txt)
    fi
    echo -e "${GRN}Done!${CLR}"
    return 0
}

build() {
    # Collect libs
    LIB=""
    echo -n "Searching for libs... "
    IFS=$'\n'
    count=0
    for lib in $(find "$LIB_DIR" -name "*.jar"); do
        if [[ "$LIB" == "" ]]; then
            LIB=$lib
        else
            LIB="$LIB:$lib"
        fi
        let count+=1
    done

    IFS=$':'
    echo -e "${GRN}Found $count.${CLR}"
    for lib in $LIB; do
        echo -e "${CYN}\t$lib${CLR}"
    done
    unset IFS

    # Collect sources
    echo -n "Searching for source files... "
    find "$SRC_DIR" -name "*.java" > /tmp/sources.txt
    echo -e "${GRN}Found $(cat /tmp/sources.txt | wc -l).${CLR}"

    # Compile
    echo -n "Building... "
    javacerror=$($JAVAC @/tmp/sources.txt -classpath "$LIB" -d "./" $1 2>&1)
    status=$?
    rm /tmp/sources.txt
    if [[ $status -eq 0 ]]; then
        echo -e "${GRN}Built $(find -name "*.class" | wc -l) files!${CLR}"
    else
        echo -e "${RED}Error while building!\n${javacerror}${CLR}"
    fi
    return $status
}

run() {
    echo -e "${GRN}Running...${CLR}"
    $JAVA Main $1
    status=$?
    return $status
}

if [[ "$1" == *"clean"* ]]; then
    clean
    status=$?
fi
if [[ "$1" == *"build"* ]] || [[ "$@" == "" ]]; then
    build $2
    status=$?
fi
if [[ "$1" == *"run"* ]] && [[ $status -eq 0 ]]; then
    run $3
    status=$?
fi
exit $status
