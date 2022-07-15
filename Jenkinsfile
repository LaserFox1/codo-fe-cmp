#!/usr/bin/env groovy
@Library('jenkins-pipeline-library')

import com.lkww.KubernetesPipeline
import com.lkww.versioning.MavenVersioner

KubernetesPipeline kubernetesPipeline = new KubernetesPipeline()

kubernetesPipeline.start('Jenkinsfile.json', 20, "XL", new MavenVersioner())
