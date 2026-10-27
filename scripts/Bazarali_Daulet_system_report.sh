#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
LOG="logs/Bazarali_Daulet_system_report.log"
mkdir -p logs
{
  printf 'Student: Daulet Bazarali | IT2-2308 | ID 37392\n'
  printf 'Date: '; date
  printf 'User: '; whoami
  printf 'Kernel: '; uname -a
  printf '\nCPU information:\n'
  if command -v lscpu >/dev/null; then lscpu | head -n 12; else sysctl -n machdep.cpu.brand_string 2>/dev/null || uname -m; fi
  printf '\nMemory:\n'
  if command -v free >/dev/null; then free -h; else vm_stat | head -n 6; fi
  printf '\nDisk usage / available disk space:\n'
  df -h .
} | tee "$LOG"
